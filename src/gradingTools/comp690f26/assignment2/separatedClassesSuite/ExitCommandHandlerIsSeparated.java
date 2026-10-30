package gradingTools.comp690f26.assignment2.separatedClassesSuite;
/**
 * Uses the exit-handler class resolved in the project and its trace role tags. A class tagged only as
 * exit passes; a class also tagged as an observer, registry or another handler
 * fails even if separate instances are used. Missing exit evidence earns no credit.
 */
@util.annotations.Explanation("Requires the exit-handler class to have no other traced role.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class ExitCommandHandlerIsSeparated extends gradingTools.comp690f26.common.AbstractClassIsSeparated {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class,
            gradingTools.comp690f26.assignment2.projectClassesSuite.ExitCommandHandlerExistsInProject.class};
    }
    @Override protected String getTag() { return eduAI.trace.processors.cli.CLIArchitectureChecks.EXIT_HANDLER_TAG; }
}
