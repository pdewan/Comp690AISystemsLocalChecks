package gradingTools.comp690f26.assignment1.expectedRequestContentSuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaImageMatchesGenericImage;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After history and handle checks, requires the assignment image, not just matching arbitrary images.")
@MaxValue(20)
public class OllamaExpectedImageMatchesGenericImage
		extends AbstractProviderExpectedContentCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				OllamaImageMatchesGenericImage.class);
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor aProcessor) {
		return aProcessor.checkExpectedGenericImagesMatch(provider());
	}
}
