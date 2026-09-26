package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
@Explanation(TraceIntegrityExplanations.PARTIAL_RESPONSE_CALLBACK_INVOKED_EXPLANATION)
public class PartialResponseCallbacksTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ProviderStreamingChunksReceivedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PARTIAL_RESPONSE_CALLBACK_INVOKED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
