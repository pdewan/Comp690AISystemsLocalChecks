package gradingTools.comp690f26.assignment1.projectClassesSuite;

import java.util.ArrayList;
import java.util.List;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;
import util.trace.Tracer;

@Explanation("Blocks integrity if a traced role lacks a project class; ignores roles absent from traces.")
public class AllTracedClassesExistInProject
		extends PassFailJUnitTestCase {

	@Override
	protected Class[] precedingTests() {
		Class<?>[] projectClassTests = Assignment1ProjectClassTests.all();
		Class<?>[] result = new Class<?>[projectClassTests.length + 1];
		result[0] = TraceFilesProvided.class;
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
		return TraceFilesProvided.class.equals(aClass);
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
						classTest.getTag()
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
