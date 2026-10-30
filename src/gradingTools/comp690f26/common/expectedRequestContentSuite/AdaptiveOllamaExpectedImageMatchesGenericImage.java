package gradingTools.comp690f26.common.expectedRequestContentSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaImageMatchesGenericImage;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Uses image values, with prerequisite policy supplied separately from prompt history.
 * Correct expected image bytes pass only after the configured constraints. Different
 * bytes fail; A1 also requires history, compaction and handle structure. */
@Explanation("Checks expected image bytes after image translation and suite-configured prerequisites.")
@MaxValue(20)
public class AdaptiveOllamaExpectedImageMatchesGenericImage
		extends AbstractAdaptiveProviderExpectedContentCheck {
    @Override protected boolean comparesImages() { return true; }
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				OllamaImageMatchesGenericImage.class);
	}
	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkExpectedGenericImagesMatch(provider());
	}
}
