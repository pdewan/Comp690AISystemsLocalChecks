package gradingTools.comp690f26.assignment1.tracedClassesSuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import util.trace.Tracer;

public abstract class AbstractClassFoundInTrace
		extends PassFailJUnitTestCase {
	protected abstract String getTag();

	@Override
	protected Class[] precedingTests() {
		return new Class<?>[]{TraceFilesProvided.class};
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		BridgeDemoTraceFilesProcessor bridgeDemoProcessor =
				((TraceFilesProvided) getFirstPrecedingTestInstance())
						.getBridgeDemoProcessor();
		String tag = getTag();
		TraceCheckResult traceCheckResult =
				bridgeDemoProcessor.checkClassExistsForTag(tag);
		for (String message : traceCheckResult.level3Messages()) {
			System.out.println(message);
		}
		if (!traceCheckResult.passed()) {
			String message =
					"Expected class not found in trace: "
							+ tag
							+ " "
							+ traceCheckResult.errors();
			Tracer.error(message);
			return fail(message);
		}
		return pass();
	}
}
