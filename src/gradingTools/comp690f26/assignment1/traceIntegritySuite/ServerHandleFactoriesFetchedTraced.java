package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AllTracedClassesExistInProject;
@Explanation(TraceIntegrityExplanations.SERVER_HANDLE_FACTORY_FETCHED_EXPLANATION)
public class ServerHandleFactoriesFetchedTraced extends AbstractTraceEventTraced {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{AllTracedClassesExistInProject.class};
	}
	protected LibTraceEvent traceEvent() { return LibTraceEvent.SERVER_HANDLE_FACTORY_FETCHED; }
}
