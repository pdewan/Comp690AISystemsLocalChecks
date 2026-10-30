package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import eduAI.trace.processors.GenericTracesFileProcessor;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandDispatcherExistsInProject;
import gradingTools.comp690f26.common.AbstractClassIsSeparated;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;

/**
 * Resolves this traced role in the project before checking class-role separation.
 * A resolved class serving only this role passes; unrelated tags on it fail.
 * Missing role or project-class evidence earns no credit through prerequisites.
 * Property-handler classes may serve multiple property roles, but no unrelated role.
 */
@util.annotations.Explanation("Checks that the dispatcher does not also implement another tagged role.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class CLICommandDispatcherIsSeparated extends AbstractClassIsSeparated {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{AdaptiveTraceFilesProvided.class, CLICommandDispatcherExistsInProject.class};
    }
    @Override protected String getTag() {
        return GenericTracesFileProcessor.CLI_COMMAND_DISPATCHER_TAG;
    }
}

