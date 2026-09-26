package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.AbstractTraceEventTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ProviderStreamingChunksReceivedTraced;
@Explanation(TraceIntegrityExplanations.STREAMING_CHUNK_ACCUMULATED_EXPLANATION)
public class StreamingChunksAccumulatedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ProviderStreamingChunksReceivedTraced.class,
				MessageMergerFactoriesFetchedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.STREAMING_CHUNK_ACCUMULATED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
