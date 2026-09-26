package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;

@Explanation(TraceIntegrityExplanations.REQUEST_SENT_EXPLANATION)
public class RequestsTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				MessageTranslationsTraced.class,
				ParametersTranslatedTraced.class);
	}
	protected LibTraceEvent traceEvent() {
		return LibTraceEvent.REQUEST_SENT;
	}
}
