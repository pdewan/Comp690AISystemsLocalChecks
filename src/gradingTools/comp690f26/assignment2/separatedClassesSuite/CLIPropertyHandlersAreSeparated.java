package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.cli.CLIPropertyHandlerTags;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIPropertyHandlersExistInProject;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;

/**
 * Resolves this traced role in the project before checking class-role separation.
 * A resolved class serving only this role passes; unrelated tags on it fail.
 * Missing role or project-class evidence earns no credit through prerequisites.
 * Property-handler classes may serve multiple property roles, but no unrelated role.
 */
@util.annotations.Explanation("Allows one implementation class for several properties, but rejects unrelated roles on that class.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class CLIPropertyHandlersAreSeparated extends AbstractTraceProcessorCheck {
    @Override protected Class<?>[] additionalPrecedingTests() {
        return new Class<?>[]{CLIPropertyHandlersExistInProject.class};
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        return CLIPropertyHandlerTags.checkSeparation(processor);
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) {
        return String.join("\n", result.level3ErrorMessages());
    }
}


