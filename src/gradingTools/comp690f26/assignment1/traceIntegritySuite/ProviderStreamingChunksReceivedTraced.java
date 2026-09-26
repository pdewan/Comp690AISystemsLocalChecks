package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
@Explanation(TraceIntegrityExplanations.PROVIDER_STREAMING_CHUNK_RECEIVED_EXPLANATION)
public class ProviderStreamingChunksReceivedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ServerHandlesFetchedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PROVIDER_STREAMING_CHUNK_RECEIVED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
