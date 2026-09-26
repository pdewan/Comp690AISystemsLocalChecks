package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiPromptMessageAdapterFoundInTrace;
import util.annotations.MaxValue;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class GeminiPromptMessageAdapterExistsInProject
		extends AbstractClassExistsInProject {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiPromptMessageAdapterFoundInTrace.class
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
