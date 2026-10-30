package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import util.annotations.Explanation;

/** Requires metadata events with their own source, handler and property fields,
 * plus inherited provider identity evidence. Missing metadata earns no credit;
 * malformed metadata fails. A missing response dump does not block this check. */
@Explanation(TraceIntegrityExplanations.METADATA_TRANSLATED_EXPLANATION)
public class MetadataTranslatedTraced extends AbstractTraceEventTraced {
	@Override
	protected LibTraceEvent traceEvent() {
		return LibTraceEvent.METADATA_TRANSLATED;
	}
}
