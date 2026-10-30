package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.common.*;


/** Validates both requests consumed by each history comparison, including the
 * preceding provider at a switch. A missing endpoint context fails; a malformed
 * request comparisons are visible prerequisites. The history comparison itself
 * remains restricted to the exchanges whose retention is being assessed. */
public abstract class AbstractProviderContextHistoryCheck extends AbstractProviderTraceCheck {
    @Override protected Class<?>[] additionalPrecedingTests() {
        return new Class<?>[]{gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class};
    }
    protected TraceFilesProcessor evidenceProcessor(TraceFilesProcessor processor) {
        var consumed = processor.historyRequestEvidence(provider());
        return TraceEvidenceView.select(processor, consumed::contains);
    }
    @Override protected Class<?>[] evidencePrecedingTests(TraceFilesProcessor processor) {
        return CLIProviderPrerequisites.checks(evidenceProcessor(processor), requestComparison());
    }
    @Override protected final TraceCheckResult check(TraceFilesProcessor processor) {
        return checkHistory(processor);
    }
    protected CLIProviderPrerequisites.Evidence requestComparison() {
        return CLIProviderPrerequisites.Evidence.CONTEXT;
    }
    protected abstract TraceCheckResult checkHistory(TraceFilesProcessor processor);
}
