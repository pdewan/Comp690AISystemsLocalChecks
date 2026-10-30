package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiResponseTypeIntegrity;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Uses provider evidence. Correct translations/callbacks retain credit when unrelated interfaces fail; missing required events or wrong values fail. */
// Retain this response check's allocation when requests and responses share a suite.
@util.annotations.MaxValue(15)
@Explanation("After type checks, finds native response values in generic data; gates text history.")
public class GeminiResponseMatchesGenericResponse
		extends AbstractProviderResponseCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericResponseMatches(provider());
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiResponseTypeIntegrity.class,
				ResponsesTranslatedTraced.class);
	}
}
