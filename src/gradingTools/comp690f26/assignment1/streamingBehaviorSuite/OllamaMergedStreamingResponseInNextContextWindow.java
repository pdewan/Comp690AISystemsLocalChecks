package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Requires visible request, accumulation, merge and partial-callback contracts.
 * Then compares merged answers with later requests; lost retained answers fail.
 * A final exchange without a later request supplies no retention evidence. */
@Explanation("After streaming integrity and callback checks, verifies merged answers in later requests.")
@MaxValue(25)
public class OllamaMergedStreamingResponseInNextContextWindow extends gradingTools.comp690f26.common.AbstractProviderTraceCheck {
	protected String provider() {
		return TraceProviderNames.OLLAMA;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[] { gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class, StreamingChunksAccumulatedTraced.class,
				StreamingChunksMergedTraced.class, OllamaPartialResponseCallbackInvoked.class, };
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkStreamingChunksReflectedInNextContextWindow(provider());
	}
}
