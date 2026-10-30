package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.BasicProjectIntrospection;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
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
					gradingTools.comp690f26.common.FieldTypePaths.findRequiredTypePath(
							taggedClass,
							requiredType,
							projectClasses,
							new HashSet<>(),
							taggedClass.getName());
			if (path == null && getFirstPrecedingTestInstance() instanceof
					gradingTools.comp690f26.files.AdaptiveTraceFilesProvided files) {
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






}
