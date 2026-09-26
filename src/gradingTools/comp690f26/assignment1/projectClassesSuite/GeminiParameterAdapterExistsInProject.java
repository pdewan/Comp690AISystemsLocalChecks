package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiParameterAdapterFoundInTrace;
import util.annotations.MaxValue;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class GeminiParameterAdapterExistsInProject
		extends AbstractClassExistsInProject {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiParameterAdapterFoundInTrace.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.GEMINI_PARAMETER_ADAPTER;
	}
}
