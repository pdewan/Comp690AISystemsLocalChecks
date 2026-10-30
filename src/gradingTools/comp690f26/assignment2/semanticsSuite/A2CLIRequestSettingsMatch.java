package gradingTools.comp690f26.assignment2.semanticsSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.cli.CLITraceExpectations;
import gradingTools.comp690f26.common.AbstractEvidenceScopedTraceCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import util.annotations.Explanation;


@Explanation("After validating native parameter translations, matches request settings to inputs. Requires requests exercising a model change, both streaming modes and a numeric-property change; missing exercise earns no credit. Attachment contents are not required.")
/**
 * First validates observed providers' parameter translations. A matching generic
 * setting cannot earn credit when its native translation is wrong.
 * Builds settings expectations directly from parsed inputs; attachment files and
 * prompt-content preparation are not prerequisites. Wrong numeric values fail even
 * when attachments are missing. Consistent
 * evidence passes; violations fail. Required evidence must be supplied before credit is earned.
 * An unchanged request sequence earns no credit; changed settings must reach
 * later requests with both modes and different selected models exercised.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PARAMETER_VALUES)
public class A2CLIRequestSettingsMatch extends AbstractEvidenceScopedTraceCheck {
    // Request integrity and parsed-input prerequisites both run visibly in JUnit.
    protected Class<?>[] additionalPrecedingTests() {
        return new Class<?>[]{CLIOuterOutputSyntax.class, gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class};
    }
    @Override protected Class<?>[] evidencePrecedingTests(eduAI.trace.processors.TraceFilesProcessor processor) {
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.checks(processor,
            gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.PARAMETERS);
    }
    protected TraceCheckResult check(TraceFilesProcessor processor) {
        for(var prerequisite:getPrecedingTestInstances()) if(prerequisite instanceof CLIOuterOutputSyntax input)
            return CLITraceExpectations.settingsFrom(input.getInteractions().orElseThrow())
                .check(processor.getTraceRecords().values(),null,CLITraceExpectations.Check.SETTINGS);
        throw new IllegalStateException("Parsed inputs prerequisite did not run");
    }
}
