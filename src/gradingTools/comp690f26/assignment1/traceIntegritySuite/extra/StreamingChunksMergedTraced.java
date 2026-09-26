package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.AbstractTraceEventTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
@Explanation(TraceIntegrityExplanations.STREAMING_CHUNKS_MERGED_EXPLANATION)
public class StreamingChunksMergedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				StreamingChunksAccumulatedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.STREAMING_CHUNKS_MERGED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
