package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.crossProviderSuite.GenericRequestStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaContextWindowTypeIntegrity;
import util.annotations.Explanation;
import util.annotations.MaxValue;
@MaxValue(15)
@Explanation("After type and cross-provider checks, compares image byte count, prefix, and suffix.")
public class OllamaImageMatchesGenericImage extends AbstractProviderRequestCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				OllamaContextWindowTypeIntegrity.class,
				NativePromptMessageAdapters.class,
				GenericRequestStructuresMatchAcrossProviders.class);
	}
	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericImagesMatch(provider());
	}
}
