package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiImageMatchesGenericImage;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After text/image translation, tracks image retention across turns; gates expected content.")
@MaxValue(10)
public class GeminiPreviousImagesRetained
		extends AbstractProviderContextHistoryCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiImageMatchesGenericImage.class);
	}
	protected TraceCheckResult check(TraceFilesProcessor processor) {
		return processor.checkPreviousImagesRetained(provider());
	}
}
