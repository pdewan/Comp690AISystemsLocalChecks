package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

@Explanation(TraceIntegrityExplanations.PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION)
public class ProviderIndependentContextWindowsPreparedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED; }
}
