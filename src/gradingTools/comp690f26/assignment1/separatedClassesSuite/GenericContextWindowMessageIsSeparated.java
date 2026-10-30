package gradingTools.comp690f26.assignment1.separatedClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GenericContextWindowMessageExistsInProject;
import gradingTools.comp690f26.common.AbstractClassIsSeparated;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;


/**
 * Extra-credit check requiring this role's traced class to resolve in the project.
 * Reuses the
 * shared role-separation check. A dedicated context-window message class passes;
 * a class also tagged for a registry or parameter-store role fails. Missing role or
 * project evidence earns no credit through the role-specific prerequisite.
 * Unrelated project-class failures do not block this check.
 */
@Explanation("Requires the context-window message class to exist and have no other traced role.")
@util.annotations.IsExtra(true)
@util.annotations.MaxValue(5)
public class GenericContextWindowMessageIsSeparated extends AbstractClassIsSeparated {
    @Override protected Class[] precedingTests() {
        return new Class[]{AdaptiveTraceFilesProvided.class, GenericContextWindowMessageExistsInProject.class};
    }

    @Override protected String getTag() {
        return Assignment1Tags.GENERIC_CONTEXT_WINDOW_MESSAGE;
    }
}
