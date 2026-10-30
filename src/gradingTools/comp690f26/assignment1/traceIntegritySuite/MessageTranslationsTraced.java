package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;

@Explanation(TraceIntegrityExplanations.MESSAGE_TRANSLATED_EXPLANATION)
public class MessageTranslationsTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ServerHandlesFetchedTraced.class);
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.MESSAGE_TRANSLATED; }
}
