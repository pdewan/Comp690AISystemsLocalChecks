package gradingTools.comp690f26.assignment2.tracedClassesSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.common.AbstractClassFoundInTrace;

@util.annotations.Explanation("Identifies the streaming observable from its notification trace and checks its class.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(0)
public class CLIStreamObservableFoundInTrace extends AbstractClassFoundInTrace {
    @Override protected String getTag() { return CLIArchitectureChecks.STREAM_OBSERVABLE_TAG; }
}
