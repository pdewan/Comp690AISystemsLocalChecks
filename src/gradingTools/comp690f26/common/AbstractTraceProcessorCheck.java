package gradingTools.comp690f26.common;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;
import util.trace.Tracer;

@MaxValue(1)
public abstract class AbstractTraceProcessorCheck
		extends PassFailJUnitTestCase {
	protected abstract TraceCheckResult check(
			TraceFilesProcessor aProcessor);

	protected String failureGuidance() {
		Explanation explanation =
				getClass().getAnnotation(Explanation.class);
		return explanation == null ? null : explanation.value();
	}

	protected String shortFailureMessage(TraceCheckResult aResult) {
		return getClass().getSimpleName() + " failed.";
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[0];
	}

	@Override
	protected Class[] precedingTests() {
		Class<?>[] additional = PrecedingTestUtils.forProvidedFiles(additionalPrecedingTests());
		Class<?>[] result = new Class<?>[additional.length + 1];
		result[0] = AdaptiveTraceFilesProvided.class;
		System.arraycopy(additional, 0, result, 1, additional.length);
		return result;
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		AdaptiveTraceFilesProvided traceFilesProvided =
				(AdaptiveTraceFilesProvided) getFirstPrecedingTestInstance();
		for (var prerequisite : getPrecedingTestInstances()) {
			if (prerequisite instanceof ExpectedContentChecksProvider inputs) {
				traceFilesProvided.getTraceFilesProcessor().setExpectedContentChecks(inputs.getExpectedContentChecks());
			}
		}
		TraceCheckResult result = check(
				traceFilesProvided.getTraceFilesProcessor());
		for (String message : result.level3Messages()) {
			System.out.println(message);
		}
		if (!result.passed()) {
			String guidance = failureGuidance();
			if (guidance != null && !guidance.isBlank()) {
				Tracer.error(" " + guidance);
			}
			return fail(shortFailureMessage(result));
		}
		return pass();
	}
}
