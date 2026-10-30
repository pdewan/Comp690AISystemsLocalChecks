package gradingTools.comp690f26.assignment2.projectClassesSuite;

import eduAI.trace.processors.GenericTracesFileProcessor;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLICommandDispatcherFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;

@util.annotations.Explanation("Checks that the dispatcher identified by lookup traces belongs to the submitted project.")
/**
 * Resolves the traced role to project classes. An existing class passes; a missing
 * class fails. Missing trace evidence blocks the check through its prerequisite.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROJECT_ROLE)
public class CLICommandDispatcherExistsInProject extends AbstractClassExistsInProject {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{AdaptiveTraceFilesProvided.class, CLICommandDispatcherFoundInTrace.class};
    }
    @Override protected String getTag() {
        return GenericTracesFileProcessor.CLI_COMMAND_DISPATCHER_TAG;
    }
}

