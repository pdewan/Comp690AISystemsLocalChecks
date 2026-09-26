package gradingTools.comp690f26.assignment1.interfacesSuite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.BasicProjectIntrospection;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AbstractClassExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.Assignment1ProjectClassTests;
import util.trace.Tracer;
@MaxValue(10)
@Explanation("After project lookup, rejects tagged field types recursively, including arrays and generics.")
public class TaggedClassPhysicalStructuresUseInterfaces
		extends PassFailJUnitTestCase {

	@Override
	protected Class[] precedingTests() {
		return Assignment1ProjectClassTests.all();
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		Set<Class<?>> taggedClasses = taggedClasses();
		Set<Class<?>> projectClasses = projectClasses(aProject);
		List<String> violations = new ArrayList<>();

		for (Class<?> taggedClass : taggedClasses) {
			inspectPhysicalStructure(
					taggedClass,
					taggedClass.getName(),
					taggedClasses,
					projectClasses,
					new LinkedHashSet<>(),
					violations);
		}

		if (!violations.isEmpty()) {
			String message =
					"Physical-structure fields must use interfaces instead of "
							+ "tagged classes: "
							+ violations;
			Tracer.error(message);
			return fail(message);
		}
		Tracer.info(
				"All tagged-class physical structures use interfaces: classes="
						+ classNames(taggedClasses));
		return pass();
	}

	private Set<Class<?>> taggedClasses() {
		Set<Class<?>> result = new LinkedHashSet<>();
		for (PassFailJUnitTestCase precedingTest :
				getPrecedingTestInstances()) {
			if (precedingTest instanceof AbstractClassExistsInProject classTest) {
				result.addAll(classTest.getTracedClasses());
			}
		}
		return result;
	}

	private Set<Class<?>> projectClasses(Project aProject) {
		Set<Class<?>> result = new LinkedHashSet<>();
		for (Object projectClass :
				BasicProjectIntrospection.getAllClasses(aProject)) {
			if (projectClass instanceof Class<?> type) {
				result.add(type);
			}
		}
		return result;
	}

	private void inspectPhysicalStructure(
			Class<?> aCurrentClass,
			String aPath,
			Set<Class<?>> taggedClasses,
			Set<Class<?>> projectClasses,
			Set<Class<?>> visited,
			List<String> violations) {
		if (aCurrentClass == null
				|| aCurrentClass.isInterface()
				|| !visited.add(aCurrentClass)) {
			return;
		}
		for (Class<?> current = aCurrentClass;
				current != null && current != Object.class;
				current = current.getSuperclass()) {
			for (Field field : current.getDeclaredFields()) {
				String fieldPath =
						aPath
								+ "."
								+ field.getName()
								+ ":"
								+ field.getGenericType().getTypeName();
				Set<Class<?>> referenced = referencedClasses(
						field.getGenericType());
				for (Class<?> referencedClass : referenced) {
					Class<?> componentClass = componentClass(referencedClass);
					if (taggedClasses.contains(componentClass)) {
						violations.add(
								fieldPath
										+ " uses tagged class "
										+ componentClass.getName());
					}
					if (projectClasses.contains(componentClass)
							&& !componentClass.isInterface()) {
						inspectPhysicalStructure(
								componentClass,
								fieldPath,
								taggedClasses,
								projectClasses,
								visited,
								violations);
					}
				}
			}
		}
	}

	private Set<Class<?>> referencedClasses(Type aType) {
		Set<Class<?>> result = new LinkedHashSet<>();
		collectReferencedClasses(aType, result);
		return result;
	}

	private void collectReferencedClasses(
			Type aType,
			Set<Class<?>> result) {
		if (aType instanceof Class<?> typeClass) {
			result.add(typeClass);
		} else if (aType instanceof ParameterizedType parameterizedType) {
			collectReferencedClasses(parameterizedType.getRawType(), result);
			for (Type argument : parameterizedType.getActualTypeArguments()) {
				collectReferencedClasses(argument, result);
			}
		} else if (aType instanceof GenericArrayType arrayType) {
			collectReferencedClasses(
					arrayType.getGenericComponentType(),
					result);
		} else if (aType instanceof WildcardType wildcardType) {
			for (Type bound : wildcardType.getUpperBounds()) {
				collectReferencedClasses(bound, result);
			}
			for (Type bound : wildcardType.getLowerBounds()) {
				collectReferencedClasses(bound, result);
			}
		} else if (aType instanceof TypeVariable<?> typeVariable) {
			for (Type bound : typeVariable.getBounds()) {
				collectReferencedClasses(bound, result);
			}
		}
	}

	private Class<?> componentClass(Class<?> aClass) {
		Class<?> result = aClass;
		while (result.isArray()) {
			result = result.getComponentType();
		}
		return result;
	}

	private List<String> classNames(Set<Class<?>> someClasses) {
		List<String> result = new ArrayList<>();
		for (Class<?> type : someClasses) {
			result.add(type.getName());
		}
		return result;
	}
}
