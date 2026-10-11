package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AllTracedClassesExistInProject;
@Explanation(TraceIntegrityExplanations.PROVIDER_NAMES_CONSISTENT_EXPLANATION)
public class TraceProviderNamesConsistent extends AbstractTraceProcessorCheck {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{AllTracedClassesExistInProject.class,
				ServerHandleFactoriesFetchedTraced.class};
	}

	protected TraceCheckResult check(TraceFilesProcessor p) { return p.checkProviderNamesConsistent(); }

	@Override
	protected String failureGuidance() {
		return TraceIntegrityExplanations.PROVIDER_NAMES_CONSISTENT;
	}

	@Override
	protected String shortFailureMessage(TraceCheckResult aResult) {
		return "A trace file contains an inconsistent provider name. "
				+ "See trace output for details.";
	}
}
