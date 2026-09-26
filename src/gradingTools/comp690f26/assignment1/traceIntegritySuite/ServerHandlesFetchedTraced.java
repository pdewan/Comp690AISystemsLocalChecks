package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
@Explanation(TraceIntegrityExplanations.SERVER_HANDLE_FETCHED_EXPLANATION)
public class ServerHandlesFetchedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ServerHandleFactoriesFetchedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.SERVER_HANDLE_FETCHED; }
}
