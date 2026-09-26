package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiContextWindowTypeIntegrity;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@MaxValue(20)
@Explanation("After response/type checks, finds native text in generic data; gates history/cross-provider checks.")
public class GeminiTextualContextWindowMatchesGenericContextWindow extends AbstractProviderRequestCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiContextWindowTypeIntegrity.class,
				GeminiResponseMatchesGenericResponse.class,
				NativePromptMessageAdapters.class);
	}

	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p) {
		return p.checkProviderGenericTextualContextWindowMatches(provider());
	}
}
