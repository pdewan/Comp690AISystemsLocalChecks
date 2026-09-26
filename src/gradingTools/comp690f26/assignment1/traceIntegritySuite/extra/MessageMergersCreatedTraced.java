package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;
import util.annotations.Explanation;
import java.util.List;
import java.util.Map;
import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.AbstractTraceEventTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
@Explanation(TraceIntegrityExplanations.MESSAGE_MERGER_CREATED_EXPLANATION)
public class MessageMergersCreatedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.MESSAGE_MERGER_CREATED; }
	protected Map<String, List<StreamingMode>> expectedProviderModes() { return Map.of(GEMINI, STREAMING_ONLY, OLLAMA, STREAMING_ONLY); }
}
