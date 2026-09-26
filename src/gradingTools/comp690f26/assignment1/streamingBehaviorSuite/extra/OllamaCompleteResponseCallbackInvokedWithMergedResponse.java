package gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.AbstractProviderStreamingCheck;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaPartialResponseCallbackInvoked;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import util.annotations.Explanation;

@Explanation("After partial-callback checks, requires the final callback argument to equal the merged answer.")
public class OllamaCompleteResponseCallbackInvokedWithMergedResponse
		extends AbstractProviderStreamingCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{CompleteResponseCallbacksTraced.class,
			OllamaPartialResponseCallbackInvoked.class,
};
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor processor) {
		return processor.checkCompleteResponseCallbacksInvoked(provider());
	}
}
