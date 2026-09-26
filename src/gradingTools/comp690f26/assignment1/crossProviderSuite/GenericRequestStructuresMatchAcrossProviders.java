package gradingTools.comp690f26.assignment1.crossProviderSuite;
import util.annotations.Explanation;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
@Explanation("After text and parameter matches, compares ordered canonical inputs; gates image/content checks.")
public class GenericRequestStructuresMatchAcrossProviders extends AbstractTraceProcessorCheck {
	protected Class<?>[] additionalPrecedingTests(){return new Class<?>[]{
		RequestsTraced.class,
		GeminiTextualContextWindowMatchesGenericContextWindow.class,
		OllamaTextualContextWindowMatchesGenericContextWindow.class,
		GeminiParametersMatchGenericParameters.class,
		OllamaParametersMatchGenericParameters.class
	};}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p){return p.checkProviderIndependentNonStreamingRequestsMatchAcrossProviders();}
}
