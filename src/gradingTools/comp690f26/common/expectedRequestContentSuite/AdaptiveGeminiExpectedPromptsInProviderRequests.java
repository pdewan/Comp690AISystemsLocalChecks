package gradingTools.comp690f26.common.expectedRequestContentSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Requires the configured finale constraints before accepting prescribed prompts.
 * A1 requires history, compaction, handle structure and cross-provider request
 * agreement. Present text cannot pass when one of those required checks fails. */
@Explanation("After configured constraints and request translation, requires prescribed system/user text.")
@MaxValue(10)
public class AdaptiveGeminiExpectedPromptsInProviderRequests
		extends AbstractAdaptiveProviderExpectedContentCheck {
    @Override protected boolean comparesImages() { return false; }
	@Override
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			GeminiTextualContextWindowMatchesGenericContextWindow.class);
	}

	@Override
	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkExpectedPrompts(provider());
	}
}

