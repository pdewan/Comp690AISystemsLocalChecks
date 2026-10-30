package gradingTools.comp690f26.assignment2.parameterHistorySuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.support.AbstractCLIArchitectureCheck;


@util.annotations.Explanation("After visible request and parameter-translation checks, verifies snapshots at setter checkpoints. Requires two earlier requests and a changed setter; missing or changed snapshots fail.")
/**
 * Uses visible A1 request and parameter-translation prerequisites for providers
 * observed before the last setter. Corrupt parameters, missing checkpoints or
 * altered snapshots fail. No qualifying
 * two-request/change/checkpoint sequence earns no credit.
 * Getter defaults need not be known: only setters define checkpoint expectations.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.HISTORY_RETENTION)
public class A2CLIParameterHistorySnapshotsPreserved extends AbstractCLIArchitectureCheck {
    @Override protected Class<?>[] additionalPrecedingTests() {
        return gradingTools.comp690f26.common.PrecedingTestUtils.add(super.additionalPrecedingTests(),
            gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class);
    }
    private gradingTools.comp690f26.common.io.CLIOuterOutputSyntax inputs() {
        return getPrecedingTestInstances().stream()
            .filter(gradingTools.comp690f26.common.io.CLIOuterOutputSyntax.class::isInstance)
            .map(gradingTools.comp690f26.common.io.CLIOuterOutputSyntax.class::cast).findFirst()
            .orElseThrow(()->new IllegalStateException("Parsed input prerequisite did not run"));
    }
    private int lastSetter() {
        return inputs().getInteractions().orElseThrow().entries().stream()
            .filter(e->e.input().kind()==eduAI.trace.processors.cli.CLIInteraction.Kind.PROPERTY_SET)
            .mapToInt(eduAI.trace.processors.cli.CLIMixedIO.Entry::number).max()
            .orElseThrow(()->new IllegalArgumentException("Insufficient evidence: no property setters"));
    }
    private eduAI.trace.processors.TraceFilesProcessor evidenceProcessor(eduAI.trace.processors.TraceFilesProcessor processor) {
        int setter=lastSetter();
        var ends=new java.util.HashMap<String,Integer>();
        for(var r:processor.getTraceRecords().values()) if(eduAI.trace.processors.cli.CLITraceEvidence.commandResult(r)
                && eduAI.trace.processors.cli.CLITraceEvidence.number(r.getTraceLine().getAuxiliaryData())==setter)
            ends.merge(r.getTraceFileName(),r.getTraceFileLineNumber(),Math::max);
        if(ends.isEmpty()) throw new IllegalArgumentException("Missing command return for final property setter "+setter);
        return gradingTools.comp690f26.common.TraceEvidenceView.select(processor,
            r->ends.containsKey(r.getTraceFileName()) && r.getTraceFileLineNumber()<=ends.get(r.getTraceFileName()));
    }
    @Override protected Class<?>[] evidencePrecedingTests(eduAI.trace.processors.TraceFilesProcessor processor) {
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.checks(evidenceProcessor(processor),
            gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.PARAMETERS);
    }
    @Override protected eduAI.trace.processors.TraceCheckResult check(eduAI.trace.processors.TraceFilesProcessor processor) {
        var relevant=evidenceProcessor(processor);
        int setter=lastSetter();
        var io=new eduAI.trace.processors.cli.CLIMixedIO(inputs().getInteractions().orElseThrow().entries().stream()
            .filter(e->e.number()<=setter).toList());
        return CLIArchitectureChecks.check(architectureCheck(),relevant.getTraceRecords().values(),io,
            inputs().getInteractionsFile().getParent());
    }
    // Setters before the first request do not exercise preservation and earn no credit.
    @Override protected CLIArchitectureChecks.Check architectureCheck() { return CLIArchitectureChecks.Check.HISTORY; }
}
