package gradingTools.comp690f26.common;

import eduAI.trace.processors.*;


/** Response translations used as a retention oracle are visible prerequisites.
 * The retention comparison still uses only responses with a later request. */
public abstract class AbstractCompletedResponseRetentionCheck extends
        gradingTools.comp690f26.assignment1.contextHistorySuite.AbstractProviderContextHistoryCheck {
    @Override protected Class<?>[] evidencePrecedingTests(TraceFilesProcessor processor) {
        var consumed = processor.completedResponseRetentionEvidence(provider());
        if (!consumed.isEmpty()) {
            var evidence = TraceEvidenceView.select(processor, consumed::contains);
            return PrecedingTestUtils.add(super.evidencePrecedingTests(processor),
                CLIProviderPrerequisites.checks(evidence, CLIProviderPrerequisites.Evidence.RESPONSE));
        }
        return super.evidencePrecedingTests(processor);
    }
    @Override protected TraceCheckResult checkHistory(TraceFilesProcessor processor) {
        return processor.checkPreviousCompletedResponsesRetained(provider());
    }
}
