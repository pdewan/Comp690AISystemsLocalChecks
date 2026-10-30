package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

@Explanation(TraceIntegrityExplanations.SERVER_HANDLE_FACTORY_FETCHED_EXPLANATION)
public class ServerHandleFactoriesFetchedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[0];
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.SERVER_HANDLE_FACTORY_FETCHED; }
}
