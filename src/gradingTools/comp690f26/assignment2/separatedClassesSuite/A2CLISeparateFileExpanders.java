package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIExpanderSeparationChecks;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;


/**
 * Uses traced expanded-value kinds and expansion traces to check expander object roles.
 * Stable text and image expanders with distinct identities pass; a shared
 * expander or a changed identity for one kind fails. Missing expansion traces
 * earn no credit; both value kinds are required. No attachment files are read. Expanded contents are checked by
 * A2CLITextFileExpansionContentsMatch and A2CLIImageFileExpansionContentsMatch.
 */
@util.annotations.Explanation("Requires both traced value kinds; checks distinct text/image expander objects and reuse within each kind; does not compare expanded contents.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class A2CLISeparateFileExpanders extends AbstractTraceProcessorCheck {
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        return CLIExpanderSeparationChecks.check(processor.getTraceRecords().values());
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) {
        return String.join("; ", result.level3ErrorMessages());
    }
}
