package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.lib.LibTraceEvent;
@Explanation(TraceIntegrityExplanations.PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION)
public class ProviderDependentContextWindowsPreparedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED; }
}
