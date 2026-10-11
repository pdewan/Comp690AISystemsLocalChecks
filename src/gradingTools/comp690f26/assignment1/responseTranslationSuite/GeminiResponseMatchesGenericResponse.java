package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import util.annotations.Explanation;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMessageAdapters;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiResponseTypeIntegrity;

@Explanation("After type/interface checks, finds native response values in generic data; gates text history.")
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
				ResponsesTranslatedTraced.class,
				NativeResponseMessageAdapters.class);
	}
}
