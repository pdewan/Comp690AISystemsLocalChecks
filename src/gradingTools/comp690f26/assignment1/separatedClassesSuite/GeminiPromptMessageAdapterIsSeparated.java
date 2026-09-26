package gradingTools.comp690f26.assignment1.separatedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiPromptMessageAdapterExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, rejects a class serving this role and any other trace-derived role.")
public class GeminiPromptMessageAdapterIsSeparated
		extends AbstractClassIsSeparated {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
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
