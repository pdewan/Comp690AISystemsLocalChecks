package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.crossProviderSuite.GenericRequestStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiContextWindowTypeIntegrity;
import util.annotations.Explanation;
import util.annotations.MaxValue;
@MaxValue(25)
@Explanation("After type and cross-provider checks, compares image byte count, prefix, and suffix.")
public class GeminiImageMatchesGenericImage extends AbstractProviderRequestCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiContextWindowTypeIntegrity.class,
				NativePromptMessageAdapters.class,
				GenericRequestStructuresMatchAcrossProviders.class);
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericImagesMatch(provider());
	}
}
