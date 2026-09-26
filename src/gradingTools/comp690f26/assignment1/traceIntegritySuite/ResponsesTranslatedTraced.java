package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;

@Explanation(TraceIntegrityExplanations.RESPONSE_TRANSLATED_EXPLANATION)
public class ResponsesTranslatedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ServerHandlesFetchedTraced.class);
	}
	@Override
	protected LibTraceEvent traceEvent() {
		return LibTraceEvent.RESPONSE_TRANSLATED;
	}
}
