package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.lib.LibTraceEvent;
@Explanation(TraceIntegrityExplanations.METADATA_PROPERTY_STORE_CREATED_EXPLANATION)
public class MetadataPropertyStoresCreatedTraced extends AbstractTraceEventTraced {
	protected LibTraceEvent traceEvent() { return LibTraceEvent.METADATA_PROPERTY_STORE_CREATED; }
}
