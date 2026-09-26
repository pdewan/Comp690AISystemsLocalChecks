package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;

@Explanation(TraceIntegrityExplanations.METADATA_TRANSLATED_EXPLANATION)
public class MetadataTranslatedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				ResponsesTranslatedTraced.class);
	}
	@Override
	protected LibTraceEvent traceEvent() {
		return LibTraceEvent.METADATA_TRANSLATED;
	}
}
