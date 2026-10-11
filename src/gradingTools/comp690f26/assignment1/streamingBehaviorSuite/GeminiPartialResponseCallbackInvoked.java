package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import util.annotations.Explanation;
import util.annotations.MaxValue;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeChunkCallbackAdapters;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.PartialResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ProviderStreamingChunksReceivedTraced;

@Explanation("After chunk/callback evidence, requires single chunks, not running totals; gates merge/completion.")
@MaxValue(10)
public class GeminiPartialResponseCallbackInvoked extends AbstractProviderStreamingCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[] { ProviderStreamingChunksReceivedTraced.class,
				PartialResponseCallbacksTraced.class,
				NativeChunkCallbackAdapters.class,};
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkPartialResponseCallbacksInvoked(provider());
	}
}
