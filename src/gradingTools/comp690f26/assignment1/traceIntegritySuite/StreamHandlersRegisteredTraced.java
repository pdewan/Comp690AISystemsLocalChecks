package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
@Explanation(TraceIntegrityExplanations.STREAM_HANDLER_REGISTERED_EXPLANATION)
public class StreamHandlersRegisteredTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.STREAM_HANDLER_REGISTERED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
