package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

/** Requires the factory lookup contract before accepting its returned handle.
 * Valid lookup and handle identities pass; missing registry/factory evidence or
 * inconsistent handle identities fail before dependent adapter checks. */
@Explanation(TraceIntegrityExplanations.SERVER_HANDLE_FETCHED_EXPLANATION)
public class ServerHandlesFetchedTraced extends AbstractTraceEventTraced {
	@Override protected Class<?>[] additionalPrecedingTests() {
		return gradingTools.comp690f26.common.PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ServerHandleFactoriesFetchedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.SERVER_HANDLE_FETCHED; }
}
