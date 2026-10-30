package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import util.annotations.Explanation;

/** Requires provider lookup keys and matching factory identities before requests.
 * Full factory trace integrity is a visible prerequisite. Missing/mismatched
 * factory identity, conflicting bindings or absent lookup evidence fail.
 * Combined sessions also require native request SDK types to match the key;
 * wrong parameter values do not make otherwise valid provider evidence fail. */
@Explanation(TraceIntegrityExplanations.PROVIDER_NAMES_CONSISTENT_EXPLANATION)
public class TraceProviderNamesConsistent extends AbstractTraceProcessorCheck {
	@Override protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{ServerHandleFactoriesFetchedTraced.class};
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
