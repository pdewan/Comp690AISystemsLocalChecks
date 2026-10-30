package gradingTools.comp690f26.common;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.trace.Tracer;


public abstract class AbstractClassFoundInTrace
		extends PassFailJUnitTestCase {
	protected abstract String getTag();

	@Override
	protected Class[] precedingTests() {
		return new Class<?>[]{AdaptiveTraceFilesProvided.class};
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		TraceFilesProcessor traceFilesProcessor =
				((AdaptiveTraceFilesProvided) getFirstPrecedingTestInstance())
						.getTraceFilesProcessor();
		String tag = getTag();
		TraceCheckResult traceCheckResult =
				traceFilesProcessor.checkClassExistsForTag(tag);
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
