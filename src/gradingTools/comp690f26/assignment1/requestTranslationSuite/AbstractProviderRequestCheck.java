package gradingTools.comp690f26.assignment1.requestTranslationSuite;

/** Uses the common request-event contract within the inherited provider scope.
 * Correct request evidence passes; absent requests fail. Value comparisons stay
 * in the concrete checks, so incorrect parameters do not veto text comparison. */
public abstract class AbstractProviderRequestCheck
		extends gradingTools.comp690f26.common.AbstractProviderTraceCheck {
	protected abstract String provider();
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{
			gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class,
		};
	}
}
