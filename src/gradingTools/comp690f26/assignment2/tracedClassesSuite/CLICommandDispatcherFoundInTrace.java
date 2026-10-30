package gradingTools.comp690f26.assignment2.tracedClassesSuite;

import eduAI.trace.processors.GenericTracesFileProcessor;
import gradingTools.comp690f26.common.AbstractClassFoundInTrace;


@util.annotations.Explanation("Identifies the dispatcher from the source class of a command-handler lookup trace.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(0)
public class CLICommandDispatcherFoundInTrace extends AbstractClassFoundInTrace {
    @Override protected String getTag() {
        return GenericTracesFileProcessor.CLI_COMMAND_DISPATCHER_TAG;
    }
}

