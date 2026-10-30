package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

/** Requires message and parameter translation evidence before accepting request
 * dumps. Complete preparation and valid requests pass; missing translation calls
 * or malformed requests fail visibly before dependent value/history checks. */
@Explanation(TraceIntegrityExplanations.REQUEST_SENT_EXPLANATION)
public class RequestsTraced extends AbstractTraceEventTraced {
	@Override protected Class<?>[] additionalPrecedingTests() {
		return gradingTools.comp690f26.common.PrecedingTestUtils.add(super.additionalPrecedingTests(),
				MessageTranslationsTraced.class, ParametersTranslatedTraced.class);
	}
	protected LibTraceEvent traceEvent() {
		return LibTraceEvent.REQUEST_SENT;
	}
}
