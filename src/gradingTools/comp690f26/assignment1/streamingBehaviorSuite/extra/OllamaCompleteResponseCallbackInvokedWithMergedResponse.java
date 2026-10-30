package gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaPartialResponseCallbackInvoked;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import util.annotations.Explanation;


/** Optional completion-callback correctness: a callback containing the merged
 * answer earns 15 extra points; missing or incorrect completion earns none.
 * Regular streaming checks do not depend on this optional callback. */
@util.annotations.IsExtra(true)
@util.annotations.MaxValue(15)
@Explanation("After partial-callback checks, requires the final callback argument to equal the merged answer.")
public class OllamaCompleteResponseCallbackInvokedWithMergedResponse
		extends gradingTools.comp690f26.common.AbstractProviderTraceCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{CompleteResponseCallbacksTraced.class,
			OllamaPartialResponseCallbackInvoked.class,
};
	}
	protected TraceCheckResult check(TraceFilesProcessor processor) {
		return processor.checkCompleteResponseCallbacksInvoked(provider());
	}
}
