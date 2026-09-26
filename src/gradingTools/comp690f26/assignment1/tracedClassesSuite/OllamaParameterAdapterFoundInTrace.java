package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import util.annotations.MaxValue;

@Explanation("Derives the role from parameter-translation evidence; supplies class names for project lookup.")
public class OllamaParameterAdapterFoundInTrace
		extends AbstractClassFoundInTrace {

	@Override
	protected String getTag() {
		return Assignment1Tags.OLLAMA_PARAMETER_ADAPTER;
	}
}
