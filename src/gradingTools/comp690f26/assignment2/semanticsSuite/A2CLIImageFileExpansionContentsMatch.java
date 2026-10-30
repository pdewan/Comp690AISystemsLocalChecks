package gradingTools.comp690f26.assignment2.semanticsSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.support.AbstractCLIArchitectureCheck;


/**
 * Matches image attachment names, counts and byte summaries against the I/O
 * session's files, independently of text expansions. Matching image evidence
 * passes; missing or duplicate expansion, wrong name or changed summary fails.
 * No image attachments means insufficient evidence and no credit. This retains
 * the existing summary comparison, not a comparison of every image byte.
 */
@util.annotations.Explanation("Checks image-file expansion names, counts and byte summaries against actual attachments. Text contents are checked separately; no image attachment means no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.ATTACHMENT_CONTENT)
public class A2CLIImageFileExpansionContentsMatch extends AbstractCLIArchitectureCheck {
    @Override protected CLIArchitectureChecks.Check architectureCheck() { return CLIArchitectureChecks.Check.IMAGE_FILE_CONTENTS; }
}
