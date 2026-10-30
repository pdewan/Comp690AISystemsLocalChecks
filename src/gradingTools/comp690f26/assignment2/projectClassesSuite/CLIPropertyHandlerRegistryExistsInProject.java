package gradingTools.comp690f26.assignment2.projectClassesSuite;

import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIPropertyHandlerRegistryFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;

/**
 * Resolves the traced role to project classes. An existing class passes; a missing
 * class fails. Missing trace evidence blocks the check through its prerequisite.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROJECT_ROLE)
public class CLIPropertyHandlerRegistryExistsInProject extends gradingTools.comp690f26.common.AbstractClassExistsInProject {
    @Override protected String getTag() { return eduAI.trace.processors.cli.CLIArchitectureChecks.PROPERTY_REGISTRY_TAG; }
    @Override protected Class[] precedingTests() { return new Class<?>[]{gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class,CLIPropertyHandlerRegistryFoundInTrace.class}; }
}
