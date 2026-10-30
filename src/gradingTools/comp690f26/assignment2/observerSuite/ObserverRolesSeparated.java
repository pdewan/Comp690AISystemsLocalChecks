package gradingTools.comp690f26.assignment2.observerSuite;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIObserverSeparationChecks;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import java.util.List;


/**
 * Uses processing identities and command/stream display observer identities
 * from traces, without input or output text. Distinct processing and display
 * objects and distinct regular/stream observers pass. A handler used as an
 * observer, or one object used for both observers, fails. Missing processing,
 * command display or stream display evidence earns no credit. This proves
 * object-role separation, not absence of all display code inside processors.
 */
@util.annotations.Explanation("Requires processing identities and both display observers. Observers must differ from processing objects and from each other; uses traces only.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.SEPARATION)
public class ObserverRolesSeparated extends AbstractTraceProcessorCheck {
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var findings = CLIObserverSeparationChecks.check(processor.getTraceRecords().values().stream()
            .map(TraceRecord::getTraceLine).toList());
        return new TraceCheckResult(List.of(), List.of(), findings.stream()
            .map(f -> f.check()+": "+f.detail()).toList());
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) {
        return String.join("\n", result.level3ErrorMessages());
    }
}
