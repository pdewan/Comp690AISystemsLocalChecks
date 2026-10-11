package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.BasicProjectIntrospection;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AbstractClassExistsInProject;
import util.trace.Tracer;

public abstract class AbstractClassHasRequiredTypes
		extends PassFailJUnitTestCase {

	protected abstract List<Class<?>> getRequiredTypes();

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		Class<?> taggedClass =
				((AbstractClassExistsInProject)
						getLastPrecedingTestInstance())
						.getTracedClass();
		Set<Class<?>> projectClasses = new HashSet<>();
		for (Object projectClass :
				BasicProjectIntrospection.getAllClasses(aProject)) {
			if (projectClass instanceof Class<?> type) {
				projectClasses.add(type);
			}
		}
		List<String> missingTypes = new ArrayList<>();

		for (Class<?> requiredType : getRequiredTypes()) {
			String path =
					findRequiredTypePath(
							taggedClass,
							requiredType,
							projectClasses,
							new HashSet<>(),
							taggedClass.getName());
			if (path == null && getFirstPrecedingTestInstance() instanceof
					gradingTools.comp690f26.assignment1.TraceFilesProvided files) {
				var processor = files.getTraceFilesProcessor();
				for (var record : processor.getTraceRecords().values()) {
					if (!"server_handle_fetched".equals(record.getTraceLine().getEventName())) continue;
					String dump = record.getTraceLine().getAuxiliaryData().get("serverHandle");
					path = runtimeRequiredTypePath(dump, taggedClass, requiredType);
					if (path != null) break;
				}
			}
			if (path == null) {
				missingTypes.add(requiredType.getName());
			} else {
				Tracer.info(
						"Required physical-structure type found: "
								+ requiredType.getName()
								+ " path="
								+ path);
			}
		}

		if (!missingTypes.isEmpty()) {
			String message =
					"Required types not found in physical structure of "
							+ taggedClass.getName()
							+ ": "
							+ missingTypes;
			Tracer.error(message);
			return fail(message);
		}
		return pass();
	}

	static String runtimeRequiredTypePath(String dump, Class<?> root, Class<?> required) {
		for (var occurrence : new eduAI.trace.processors.ReflectiveValueCollector().collectRuntimeTypes(dump)) {
			var types = occurrence.typesOnPath();
			if (!types.contains(root.getName()) || occurrence.value().equals(root.getName())) continue;
			try {
				Class<?> actual = Class.forName(occurrence.value(), false, root.getClassLoader());
				if (required.isAssignableFrom(actual)) return "runtime:" + occurrence.path();
			} catch (ClassNotFoundException | LinkageError ignored) { }
		}
		return null;
	}

	private String findRequiredTypePath(
			Class<?> aCurrentClass,
			Class<?> aRequiredType,
			Set<Class<?>> aProjectClasses,
			Set<Class<?>> aVisited,
			String aPath) {
		if (aCurrentClass == null || !aVisited.add(aCurrentClass)) {
			return null;
		}

		for (Class<?> current = aCurrentClass;
				current != null && current != Object.class;
				current = current.getSuperclass()) {
			for (Field field : current.getDeclaredFields()) {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				String fieldPath =
						aPath
								+ "."
								+ field.getName()
								+ ":"
								+ field.getGenericType().getTypeName();
				for (Class<?> referencedType :
						referencedClasses(field.getGenericType())) {
					if (aRequiredType.isAssignableFrom(referencedType)) {
						return fieldPath;
					}
				}
				Class<?> fieldType = field.getType();
				if (fieldType.isArray()) {
					fieldType = fieldType.getComponentType();
				}
				if (aProjectClasses.contains(fieldType)) {
					String nestedPath =
							findRequiredTypePath(
									fieldType,
									aRequiredType,
									aProjectClasses,
									aVisited,
									fieldPath);
					if (nestedPath != null) {
						return nestedPath;
					}
				}
			}
		}
		return null;
	}

	private Set<Class<?>> referencedClasses(Type aType) {
		Set<Class<?>> result = new HashSet<>();
		collectReferencedClasses(aType, result);
		return result;
	}

	private void collectReferencedClasses(
			Type aType,
			Set<Class<?>> aResult) {
		if (aType instanceof Class<?> typeClass) {
			aResult.add(
					typeClass.isArray()
							? typeClass.getComponentType()
							: typeClass);
		} else if (aType instanceof ParameterizedType parameterizedType) {
			collectReferencedClasses(
					parameterizedType.getRawType(),
					aResult);
			for (Type argument : parameterizedType.getActualTypeArguments()) {
				collectReferencedClasses(argument, aResult);
			}
		} else if (aType instanceof GenericArrayType arrayType) {
			collectReferencedClasses(
					arrayType.getGenericComponentType(),
					aResult);
		} else if (aType instanceof WildcardType wildcardType) {
			for (Type bound : wildcardType.getUpperBounds()) {
				collectReferencedClasses(bound, aResult);
			}
			for (Type bound : wildcardType.getLowerBounds()) {
				collectReferencedClasses(bound, aResult);
			}
		} else if (aType instanceof TypeVariable<?> typeVariable) {
			for (Type bound : typeVariable.getBounds()) {
				collectReferencedClasses(bound, aResult);
			}
		}
	}
}
