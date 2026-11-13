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

    public static String missingRoleMessage(String tag, TraceFilesProcessor processor) {
        String message = "No class was identified from trace evidence for role " + tag
            + ". This does not establish that the source class is absent. Check that the operation completed "
            + "and its role-identifying trace call was emitted. A canceled model request may provide no response, metadata, or callback evidence.";
        if (eduAI.trace.processors.cli.CLIArchitectureChecks.EXIT_HANDLER_TAG.equals(tag)) {
            var names = new java.util.TreeSet<String>();
            for (var record : processor.getTraceRecords().values()) {
                if (eduAI.trace.cli.MinimalCLITrace.Event.COMMAND_HANDLED.eventName().equals(record.getTraceLine().getEventName())) {
                    String name = record.getTraceLine().getAuxiliaryData().get(eduAI.trace.cli.MinimalCLITrace.NAME);
                    if (name != null && !name.isBlank()) names.add(name);
                }
            }
            message += " This checker identifies the exit role using command name '"
                + eduAI.trace.processors.cli.CLICommandNames.EXIT + "'; observed named commands: " + names
                + ". Check the prescribed command name before concluding that the exit handler is missing.";
        }
        return message;
    }

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
					missingRoleMessage(tag, traceFilesProcessor)
							+ " "
							+ traceCheckResult.errors();
			Tracer.error(message);
			return fail(message);
		}
		return pass();
	}
}
