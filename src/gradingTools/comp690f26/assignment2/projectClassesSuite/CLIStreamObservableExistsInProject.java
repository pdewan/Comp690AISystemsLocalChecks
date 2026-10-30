package gradingTools.comp690f26.assignment2.projectClassesSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIStreamObservableFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;

@util.annotations.Explanation("Identifies the streaming observable from its notification trace and checks its class.")
/**
 * Resolves the traced role to project classes. An existing class passes; a missing
 * class fails. Missing trace evidence blocks the check through its prerequisite.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROJECT_ROLE)
public class CLIStreamObservableExistsInProject extends AbstractClassExistsInProject {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class, CLIStreamObservableFoundInTrace.class};
    }
    @Override protected String getTag() { return CLIArchitectureChecks.STREAM_OBSERVABLE_TAG; }
}
