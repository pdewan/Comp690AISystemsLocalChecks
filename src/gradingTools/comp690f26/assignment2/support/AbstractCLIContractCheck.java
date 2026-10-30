package gradingTools.comp690f26.assignment2.support;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import java.util.List;


/** Shares evidence preparation; concrete tests select an explicit contract domain. */
public abstract class AbstractCLIContractCheck extends AbstractTraceProcessorCheck {
    protected abstract eduAI.trace.processors.cli.CLIContractDomains.Domain domain();
    @Override protected Class<?>[] additionalPrecedingTests() { return gradingTools.comp690f26.common.PrecedingTestUtils.add(
        gradingTools.comp690f26.assignment2.traceIntegritySuite.A2TraceIntegritySuite.checks(), CLIOuterOutputSyntax.class); }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        for(var prerequisite:getPrecedingTestInstances())
            if(prerequisite instanceof CLIOuterOutputSyntax input && input.getInteractions().isPresent())
                return domainResult(eduAI.trace.processors.cli.CLISixCallChecks.findings(processor.getTraceRecords().values(),
                    input.getInteractions().orElseThrow(),input.getInteractionsFile().getParent()));
        return new TraceCheckResult(List.of(),List.of(),List.of("CLI expectations unavailable"));
    }
    private TraceCheckResult domainResult(List<eduAI.trace.processors.cli.MinimalCLIContractChecks.Finding> findings) {
        var errors = findings.stream().filter(f -> {
            var category = eduAI.trace.processors.cli.CLIContractDomains.domain(f.check());
            return category == domain() || category == eduAI.trace.processors.cli.CLIContractDomains.Domain.EVIDENCE;
        }).map(f -> f.check()+": "+f.detail()).toList();
        return new TraceCheckResult(List.of(), List.of(), errors);
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
