package gradingTools.comp690f26.assignment2.projectClassesSuite;

import eduAI.trace.processors.cli.CLICommandHandlerChecks;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CommandHandlerRegistryFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;

/**
 * Resolves the traced role to project classes. An existing class passes; a missing
 * class fails. Missing trace evidence blocks the check through its prerequisite.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROJECT_ROLE)
public class CommandHandlerRegistryExistsInProject extends AbstractClassExistsInProject {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{AdaptiveTraceFilesProvided.class, CommandHandlerRegistryFoundInTrace.class};
    }
    @Override protected String getTag() {
        return CLICommandHandlerChecks.REGISTRY_TAG;
    }
}

