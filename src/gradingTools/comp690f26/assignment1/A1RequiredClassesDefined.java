package gradingTools.comp690f26.assignment1;

import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiChunkCallbackAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiServerHandleAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaServerHandleAdapterFoundInTrace;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import gradingTools.shared.testcases.utils.TaggedClassesDefined;
import util.annotations.Explanation;

@Explanation("Uses traced server-handle prerequisites before checking tagged classes for source-style grading.")
public class A1RequiredClassesDefined extends TaggedClassesDefined {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			GeminiServerHandleAdapterFoundInTrace.class,
			OllamaServerHandleAdapterFoundInTrace.class,
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

}
