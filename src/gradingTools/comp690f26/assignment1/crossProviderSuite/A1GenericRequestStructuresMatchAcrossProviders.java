package gradingTools.comp690f26.assignment1.crossProviderSuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
import util.annotations.Explanation;

/** Matching nonstreaming canonical inputs pass after their text/parameter
 * validation through visible prerequisites. Different inputs fail. */
@Explanation("Validates nonstreaming text/parameters, then compares canonical inputs across providers.")
public class A1GenericRequestStructuresMatchAcrossProviders extends gradingTools.comp690f26.common.AbstractTraceProcessorCheck {
	protected Class<?>[] additionalPrecedingTests(){return new Class<?>[]{
		RequestsTraced.class,
		GeminiTextualContextWindowMatchesGenericContextWindow.class,
		OllamaTextualContextWindowMatchesGenericContextWindow.class,
		GeminiParametersMatchGenericParameters.class,
		OllamaParametersMatchGenericParameters.class
	};}
	protected TraceCheckResult check(TraceFilesProcessor p){return p.checkProviderIndependentNonStreamingRequestsMatchAcrossProviders();}
}
