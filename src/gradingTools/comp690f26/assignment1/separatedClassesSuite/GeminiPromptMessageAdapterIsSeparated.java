package gradingTools.comp690f26.assignment1.separatedClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiPromptMessageAdapterExistsInProject;
import gradingTools.comp690f26.common.AbstractClassIsSeparated;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After project lookup, rejects a class serving this role and any other trace-derived role.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class GeminiPromptMessageAdapterIsSeparated
		extends AbstractClassIsSeparated {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			GeminiPromptMessageAdapterExistsInProject.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.GEMINI_PROMPT_MESSAGE_ADAPTER;
	}
}
