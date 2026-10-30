package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

@Explanation(TraceIntegrityExplanations.PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION)
public class ProviderDependentContextWindowsPreparedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED; }
}
