package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;

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
