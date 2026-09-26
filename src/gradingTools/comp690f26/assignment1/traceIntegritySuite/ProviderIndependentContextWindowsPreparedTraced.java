package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.lib.LibTraceEvent;
@Explanation(TraceIntegrityExplanations.PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION)
public class ProviderIndependentContextWindowsPreparedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED; }
}
