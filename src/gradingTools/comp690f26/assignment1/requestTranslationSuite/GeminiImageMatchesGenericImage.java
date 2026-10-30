package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiContextWindowTypeIntegrity;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Uses only this provider's image and context-type evidence. Correct image
 * translation passes despite incorrect parameter values; missing context or
 * different native/generic image summaries fail. */
@Explanation("After context-type checks, compares this provider's native and generic image summaries.")
public class GeminiImageMatchesGenericImage extends AbstractProviderRequestCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiContextWindowTypeIntegrity.class);
	}
	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericImagesMatch(provider());
	}
}
