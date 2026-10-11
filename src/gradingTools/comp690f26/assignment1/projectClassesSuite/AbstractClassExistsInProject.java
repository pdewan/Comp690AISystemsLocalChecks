package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;

import java.util.List;

import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.BasicProjectIntrospection;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.AbstractClassFoundInTrace;
import util.trace.Tracer;

public abstract class AbstractClassExistsInProject
		extends PassFailJUnitTestCase {
	private final List<Class<?>> tracedClasses = new java.util.ArrayList<>();
	private final List<String> tracedClassNames = new java.util.ArrayList<>();
	private boolean classFoundInTrace = true;

	protected abstract String getTag();

	public Class getTracedClass() {
		return tracedClasses.get(0);
	}

	public List<Class<?>> getTracedClasses() {
		return List.copyOf(tracedClasses);
	}

	public List<String> getTracedClassNames() {
		return List.copyOf(tracedClassNames);
	}

	public boolean isClassFoundInTrace() {
		return classFoundInTrace;
	}

	@Override
	protected boolean failedTestVetoes(Class aClass) {
		if (AbstractClassFoundInTrace.class.isAssignableFrom(aClass)) {
			classFoundInTrace = false;
		}
		return true;
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		TraceFilesProcessor traceFilesProcessor =
				((TraceFilesProvided) getFirstPrecedingTestInstance())
						.getTraceFilesProcessor();
		String tag = getTag();
		List<String> classNames = traceFilesProcessor.classesForTag(tag);
		tracedClasses.clear();
		tracedClassNames.clear();
		tracedClassNames.addAll(classNames);
		for (String className : classNames) {
			Class<?> tracedClass =
					BasicProjectIntrospection.findClass(
							aProject,
							className);
			if (tracedClass == null) {
				tracedClass = findClassByExactName(aProject, className);
			}
			if (tracedClass == null) {
				String message =
						"Expected class not found in project: "
								+ tag
								+ " "
								+ className;
				Tracer.error(message);
				return fail(message);
			}
			tracedClasses.add(tracedClass);
		}

		String message =
				"Expected classes found in project: "
						+ tag
						+ " "
						+ classNames;
		Tracer.info(message);
		return pass();
	}

	private Class<?> findClassByExactName(
			Project aProject,
			String aClassName) {
		for (Object candidate :
				BasicProjectIntrospection.getAllClasses(aProject)) {
			if (candidate instanceof Class<?> candidateClass
					&& candidateClass.getName().equals(aClassName)) {
				return candidateClass;
			}
		}
		int nestedClassSeparator = aClassName.indexOf('$');
		if (nestedClassSeparator > 0) {
			String enclosingClassName =
					aClassName.substring(0, nestedClassSeparator);
			Class<?> enclosingClass =
					BasicProjectIntrospection.findClass(
							aProject,
							enclosingClassName);
			if (enclosingClass != null) {
				try {
					return Class.forName(
							aClassName,
							false,
							enclosingClass.getClassLoader());
				} catch (ClassNotFoundException e) {
					return null;
				}
			}
		}
		return null;
	}
}
