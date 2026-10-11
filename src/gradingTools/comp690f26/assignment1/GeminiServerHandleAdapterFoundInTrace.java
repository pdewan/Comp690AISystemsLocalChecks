package gradingTools.comp690f26.assignment1;
import util.annotations.Explanation;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import util.trace.Tracer;
import util.annotations.MaxValue;
@Explanation("Derives the role from handle-fetch evidence; supplies class names for project lookup.")
@MaxValue(1)
public class GeminiServerHandleAdapterFoundInTrace extends PassFailJUnitTestCase {
//	TraceFilesProcessor traceFilesProcessor;
//	TraceCheckResult traceCheckResult;
	static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class
	};
	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}
	
	
	@Override
	public TestCaseResult test(Project aProject, boolean arg1) throws NotAutomatableException, NotGradableException {
		TraceFilesProcessor traceFilesProcessor = ((TraceFilesProvided) this.getFirstPrecedingTestInstance()).getTraceFilesProcessor();
//		traceFilesProcessor = traceFilesProcessor();
		TraceCheckResult traceCheckResult =
				traceFilesProcessor.checkClassExistsForTag(
						Assignment1Tags.GEMINI_SERVER_HANDLE_ADAPTER);
		for (String message : traceCheckResult.level3Messages()) {
			System.out.println(message);
		}
		if (!traceCheckResult.passed()) {
			String aMessage =
					"Expected class not found in trace: "
							+ Assignment1Tags.GEMINI_SERVER_HANDLE_ADAPTER
							+ " "
							+ traceCheckResult.errors();
			Tracer.error(aMessage);
			return fail(aMessage);
		}
		return pass();
	}
	
	
	

}
