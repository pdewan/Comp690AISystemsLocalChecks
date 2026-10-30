package gradingTools.comp690f26.assignment1.separatedClassesSuite;

import gradingTools.comp690f26.assignment1.*;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleFactoryExistsInProject;
import gradingTools.comp690f26.common.AbstractClassIsSeparated;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After project lookup, rejects a class serving this role and any other trace-derived role.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class GeminiServerHandleFactoryIsSeparated extends AbstractClassIsSeparated {
	protected Class[] precedingTests() {
		return new Class[] { AdaptiveTraceFilesProvided.class, GeminiServerHandleFactoryExistsInProject.class };
	}

	protected String getTag() {
		return Assignment1Tags.GEMINI_SERVER_HANDLE_FACTORY;
	}
}
