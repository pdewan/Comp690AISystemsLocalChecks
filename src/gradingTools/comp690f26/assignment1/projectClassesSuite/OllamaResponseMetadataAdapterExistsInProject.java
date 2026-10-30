package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaResponseMetadataAdapterFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class OllamaResponseMetadataAdapterExistsInProject
		extends AbstractClassExistsInProject {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			OllamaResponseMetadataAdapterFoundInTrace.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.OLLAMA_RESPONSE_METADATA_ADAPTER;
	}
}
