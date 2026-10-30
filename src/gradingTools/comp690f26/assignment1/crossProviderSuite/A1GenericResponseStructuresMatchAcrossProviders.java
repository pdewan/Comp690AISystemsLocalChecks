package gradingTools.comp690f26.assignment1.crossProviderSuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
import util.annotations.Explanation;

/** Requires visible response translation grades before comparing canonical shape.
 * The shape comparison uses nonstreaming responses; different shapes fail. */
@Explanation("After response translation checks, compares nonstreaming canonical shapes across providers.")
public class A1GenericResponseStructuresMatchAcrossProviders extends gradingTools.comp690f26.common.AbstractTraceProcessorCheck {
	protected Class<?>[] additionalPrecedingTests(){return new Class<?>[]{
		ResponsesTranslatedTraced.class,
		GeminiResponseMatchesGenericResponse.class,
		OllamaResponseMatchesGenericResponse.class
	};}
	protected TraceCheckResult check(TraceFilesProcessor p){return p.checkProviderIndependentNonStreamingResponsesMatchAcrossProviders();}
}
