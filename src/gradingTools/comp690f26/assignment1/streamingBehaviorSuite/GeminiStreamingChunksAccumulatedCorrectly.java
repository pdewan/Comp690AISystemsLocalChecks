package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After accumulation/merge evidence, compares each running result with chunks received so far.")
@MaxValue(10)
public class GeminiStreamingChunksAccumulatedCorrectly
		extends AbstractProviderStreamingCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{StreamingChunksAccumulatedTraced.class,
				StreamingChunksMergedTraced.class};
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor processor) {
		return processor.checkStreamingChunksAccumulatedCorrectly(provider());
	}
}
