package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import eduAI.trace.processors.cli.CLICommandHandlerChecks;
import gradingTools.comp690f26.assignment2.projectClassesSuite.PropertyCommandHandlerExistsInProject;
import gradingTools.comp690f26.common.AbstractClassIsSeparated;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;

/**
 * Resolves this traced role in the project before checking class-role separation.
 * A resolved class serving only this role passes; unrelated tags on it fail.
 * Missing role or project-class evidence earns no credit through prerequisites.
 * Property-handler classes may serve multiple property roles, but no unrelated role.
 */
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class PropertyCommandHandlerIsSeparated extends AbstractClassIsSeparated {
    @Override protected Class[] precedingTests() {
        return new Class<?>[]{AdaptiveTraceFilesProvided.class, PropertyCommandHandlerExistsInProject.class};
    }
    @Override protected String getTag() {
        return CLICommandHandlerChecks.PROPERTY_TAG;
    }
}

