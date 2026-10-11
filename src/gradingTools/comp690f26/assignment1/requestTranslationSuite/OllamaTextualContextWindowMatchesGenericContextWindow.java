package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaContextWindowTypeIntegrity;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@MaxValue(15)
@Explanation("After response/type checks, finds native text in generic data; gates history/cross-provider checks.")
public class OllamaTextualContextWindowMatchesGenericContextWindow extends AbstractProviderRequestCheck {
	protected String provider() {
		return TraceProviderNames.OLLAMA;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				OllamaContextWindowTypeIntegrity.class,
				OllamaResponseMatchesGenericResponse.class,
				NativePromptMessageAdapters.class);
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkProviderGenericTextualContextWindowMatches(provider());
	}
}
