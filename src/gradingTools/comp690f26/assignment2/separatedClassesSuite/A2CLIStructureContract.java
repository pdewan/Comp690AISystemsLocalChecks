package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.MinimalCLIContractChecks;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import java.util.List;


/**
 * Checks participant identities and registry associations directly from traces.
 * Stable distinct roles and correctly paired registry entries pass; shared roles,
 * changed identities or missing structural evidence fail. Incorrect I/O, missing
 * attachments and request/callback content failures do not block this assessment.
 */
@util.annotations.Explanation("Checks traced participant identities, registry associations and role separation; requires structural evidence, independently of I/O and model contents.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class A2CLIStructureContract extends AbstractTraceProcessorCheck {
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var traces=processor.getTraceRecords().values().stream()
            .sorted(java.util.Comparator.comparingInt(TraceRecord::getTraceFileLineNumber))
            .map(TraceRecord::getTraceLine).toList();
        var errors=MinimalCLIContractChecks.checkStructure(traces).stream()
            .map(f->f.check()+": "+f.detail()).toList();
        return new TraceCheckResult(List.of(),List.of(),errors);
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) {
        return String.join("; ",result.level3ErrorMessages());
    }
}
