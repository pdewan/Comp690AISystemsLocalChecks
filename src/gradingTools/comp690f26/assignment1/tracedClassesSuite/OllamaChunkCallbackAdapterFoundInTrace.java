package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import util.annotations.MaxValue;

@Explanation("Derives the role from callback evidence; supplies class names for project lookup.")
public class OllamaChunkCallbackAdapterFoundInTrace
		extends AbstractClassFoundInTrace {

	@Override
	protected String getTag() {
		return Assignment1Tags.OLLAMA_CHUNK_CALLBACK_ADAPTER;
	}
}
