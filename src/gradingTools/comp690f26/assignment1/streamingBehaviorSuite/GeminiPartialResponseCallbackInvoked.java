package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.PartialResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ProviderStreamingChunksReceivedTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Uses provider evidence. Correct translations/callbacks retain credit when unrelated interfaces fail; missing required events or wrong values fail. */
@Explanation("After chunk/callback evidence, requires single chunks, not running totals; gates merge/completion.")
@MaxValue(10)
public class GeminiPartialResponseCallbackInvoked extends gradingTools.comp690f26.common.AbstractProviderTraceCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[] { ProviderStreamingChunksReceivedTraced.class,
				PartialResponseCallbacksTraced.class,};
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkPartialResponseCallbacksInvoked(provider());
	}
}
