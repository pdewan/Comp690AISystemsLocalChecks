package gradingTools.comp690f26.assignment1.commonInterfacesSuite;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import util.trace.Tracer;


public abstract class AbstractClassesImplementCommonInterface
		extends PassFailJUnitTestCase {

	protected abstract List<Class<?>> getClasses();

	protected List<Class<?>> getClassesFromPrecedingProjectTests() {
		List<Class<?>> result = new ArrayList<>();
		for (PassFailJUnitTestCase precedingTest :
				getPrecedingTestInstances()) {
			if (precedingTest
					instanceof AbstractClassExistsInProject classTest) {
				result.addAll(classTest.getTracedClasses());
			}
		}
		return result;
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		List<Class<?>> classes = getClasses();
		if (classes.size() < 2) {
			String message =
					"Expected at least two classes for common-interface check: "
							+ classNames(classes);
			Tracer.error(message);
			return fail(message);
		}

		Set<Class<?>> commonInterfaces = null;
		for (Class<?> type : classes) {
			Set<Class<?>> implementedInterfaces = gradingTools.comp690f26.common.InterfaceStructure.allInterfaces(type);
			if (commonInterfaces == null) {
				commonInterfaces = implementedInterfaces;
			} else {
				commonInterfaces.retainAll(implementedInterfaces);
			}
		}
		if (commonInterfaces == null || commonInterfaces.isEmpty()) {
			String message =
					"Expected classes to implement a common interface: classes="
							+ classNames(classes);
			Tracer.error(message);
			return fail(message);
		}
		Set<Class<?>> mostSpecificCommonInterfaces =
				gradingTools.comp690f26.common.InterfaceStructure.mostSpecificInterfaces(commonInterfaces);
		if (mostSpecificCommonInterfaces.size() != 1) {
			String message =
					"Expected classes to implement one common interface: classes="
							+ classNames(classes)
							+ " commonInterfaces="
							+ classNames(
									new ArrayList<>(
											mostSpecificCommonInterfaces));
			Tracer.error(message);
			return fail(message);
		}
		Class<?> commonInterface =
				mostSpecificCommonInterfaces.iterator().next();

		Tracer.info(
				"Classes implement common interface: classes="
						+ classNames(classes)
						+ " commonInterface="
						+ commonInterface.getName());
		return pass();
	}







	private List<String> classNames(List<Class<?>> aClasses) {
		List<String> result = new ArrayList<>();
		for (Class<?> type : aClasses) {
			result.add(type.getName());
		}
		return result;
	}
}
