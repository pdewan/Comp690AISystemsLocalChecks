package gradingTools.comp690f26.assignment1.crossProviderSuite;
import util.annotations.Explanation;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
@Explanation("After response matches, compares canonical shape, not reply wording or metadata values.")
public class GenericResponseStructuresMatchAcrossProviders extends AbstractTraceProcessorCheck {
	protected Class<?>[] additionalPrecedingTests(){return new Class<?>[]{
		ResponsesTranslatedTraced.class,
		GeminiResponseMatchesGenericResponse.class,
		OllamaResponseMatchesGenericResponse.class
	};}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p){return p.checkProviderIndependentNonStreamingResponsesMatchAcrossProviders();}
}
