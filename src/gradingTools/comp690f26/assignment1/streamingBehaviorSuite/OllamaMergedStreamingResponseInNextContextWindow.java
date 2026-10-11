package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import util.annotations.Explanation;
import util.annotations.MaxValue;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;

@Explanation("After partial-callback and merge evidence, checks the full answer is retained for the next turn.")
@MaxValue(25)
public class OllamaMergedStreamingResponseInNextContextWindow extends AbstractProviderStreamingCheck {
	protected String provider() {
		return TraceProviderNames.OLLAMA;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[] { RequestsTraced.class, StreamingChunksAccumulatedTraced.class,
				StreamingChunksMergedTraced.class, OllamaPartialResponseCallbackInvoked.class, };
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkStreamingChunksReflectedInNextContextWindow(provider());
	}
}
