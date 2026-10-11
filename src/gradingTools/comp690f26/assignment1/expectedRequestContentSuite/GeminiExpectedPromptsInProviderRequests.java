package gradingTools.comp690f26.assignment1.expectedRequestContentSuite;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.crossProviderSuite.GenericRequestStructuresMatchAcrossProviders;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After history, handle, and cross-provider checks, requires the assignment's system/user prompts.")
@MaxValue(10)
public class GeminiExpectedPromptsInProviderRequests
		extends AbstractProviderExpectedContentCheck {
	@Override
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			GeminiTextualContextWindowMatchesGenericContextWindow.class,
			GenericRequestStructuresMatchAcrossProviders.class);
	}

	@Override
	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkExpectedPrompts(provider());
	}
}
