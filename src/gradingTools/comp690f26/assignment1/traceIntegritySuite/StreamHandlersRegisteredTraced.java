package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import java.util.List;
import java.util.Map;
import util.annotations.Explanation;

@Explanation(TraceIntegrityExplanations.STREAM_HANDLER_REGISTERED_EXPLANATION)
public class StreamHandlersRegisteredTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.STREAM_HANDLER_REGISTERED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
