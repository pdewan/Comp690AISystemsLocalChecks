package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.AbstractTraceEventTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ProviderStreamingChunksReceivedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import java.util.List;
import java.util.Map;
import util.annotations.Explanation;

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
