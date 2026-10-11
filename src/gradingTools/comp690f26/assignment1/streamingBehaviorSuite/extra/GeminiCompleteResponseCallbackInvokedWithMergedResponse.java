package gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.AbstractProviderStreamingCheck;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiPartialResponseCallbackInvoked;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import util.annotations.Explanation;

@Explanation("After partial-callback checks, requires the final callback argument to equal the merged answer.")
public class GeminiCompleteResponseCallbackInvokedWithMergedResponse
		extends AbstractProviderStreamingCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{CompleteResponseCallbacksTraced.class,
			GeminiPartialResponseCallbackInvoked.class	};
	}
	protected TraceCheckResult check(TraceFilesProcessor processor) {
		return processor.checkCompleteResponseCallbacksInvoked(provider());
	}
}
