package gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Inspects generic store types only. A readable store with no native SDK types passes; missing generic store or SDK types fail. Requires the common request trace prerequisite within the selected provider evidence. */
// Type validation remains a zero-point prerequisite when grouped with value comparisons.
@MaxValue(0)
@Explanation("Rejects native SDK types on generic store paths before parameter comparisons.")
public class GeminiParameterStoreTypeIntegrity
		extends AbstractProviderIndependentTypeIntegrityCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class};
	}
	protected TraceCheckResult check(TraceFilesProcessor processor) {
		return processor.checkProviderIndependentParameterStoreTypes(provider());
	}
}
