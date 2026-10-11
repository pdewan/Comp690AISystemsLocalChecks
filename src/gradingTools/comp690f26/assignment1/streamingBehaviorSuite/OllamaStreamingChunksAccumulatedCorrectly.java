package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After accumulation/merge evidence, compares each running result with chunks received so far.")
@MaxValue(10)
public class OllamaStreamingChunksAccumulatedCorrectly
		extends AbstractProviderStreamingCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{StreamingChunksAccumulatedTraced.class,
				StreamingChunksMergedTraced.class};
	}
	protected TraceCheckResult check(TraceFilesProcessor processor) {
		return processor.checkStreamingChunksAccumulatedCorrectly(provider());
	}
}
