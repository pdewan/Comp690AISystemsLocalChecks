package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import util.annotations.Explanation;
import util.annotations.MaxValue;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;

@Explanation("After partial-callback and merge evidence, checks the full answer is retained for the next turn.")
@MaxValue(25)
public class GeminiMergedStreamingResponseInNextContextWindow extends AbstractProviderStreamingCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[] { RequestsTraced.class,
			    StreamingChunksAccumulatedTraced.class,
				StreamingChunksMergedTraced.class,
				GeminiPartialResponseCallbackInvoked.class};
	}

	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p) {
		return p.checkStreamingChunksReflectedInNextContextWindow(provider());
	}
}
