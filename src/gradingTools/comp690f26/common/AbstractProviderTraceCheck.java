package gradingTools.comp690f26.common;

/** Provider comparisons keep their semantic scope; prerequisite contracts run
 * through JUnit and have their own visible grades. */
public abstract class AbstractProviderTraceCheck extends AbstractEvidenceScopedTraceCheck {
    protected abstract String provider();
}
