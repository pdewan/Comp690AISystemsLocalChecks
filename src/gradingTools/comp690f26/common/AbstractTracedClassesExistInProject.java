package gradingTools.comp690f26.common;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.util.ArrayList;
import java.util.List;
import util.annotations.Explanation;
import util.trace.Tracer;


/**
 * Aggregates explicitly supplied project-role checks. A traced class that cannot
 * be resolved fails; an untraced role is left to the required-role checks.
 * Subclasses supply role checks, never the aggregate itself.
 */
@Explanation("Checks project classes for all traced roles; event checks resolve their own participating classes.")
public abstract class AbstractTracedClassesExistInProject
		extends PassFailJUnitTestCase {

	protected abstract Class<?>[] projectClassTests();

	@Override
	protected Class[] precedingTests() {
		Class<?>[] projectClassTests = projectClassTests();
		Class<?>[] result = new Class<?>[projectClassTests.length + 1];
		result[0] = AdaptiveTraceFilesProvided.class;
		System.arraycopy(
				projectClassTests,
				0,
				result,
				1,
				projectClassTests.length);
		return result;
	}

	@Override
	protected boolean failedTestVetoes(Class aClass) {
		return AdaptiveTraceFilesProvided.class.equals(aClass);
	}

	@Override
	protected boolean shouldScaleResult() {
		return false;
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		List<String> missingClasses = new ArrayList<>();
		for (PassFailJUnitTestCase precedingTest :
				getPrecedingTestInstances()) {
			if (!(precedingTest
					instanceof AbstractClassExistsInProject classTest)
					|| !classTest.isClassFoundInTrace()) {
				continue;
			}
			TestCaseResult result = classTest.getLastResult();
			if (result == null || !result.isPass()) {
				missingClasses.add(
						classTest.getClassTag()
								+ "="
								+ classTest.getTracedClassNames());
			}
		}
		if (!missingClasses.isEmpty()) {
			String message =
					"Classes identified by traces are absent from the project: "
							+ missingClasses;
			Tracer.error(message);
			return fail(message);
		}
		Tracer.info("All classes identified by traces exist in the project.");
		return pass();
	}
}
