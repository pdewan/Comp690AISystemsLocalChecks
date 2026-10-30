package gradingTools.comp690f26.assignment2.semanticsSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.support.AbstractCLIArchitectureCheck;


/**
 * Matches text attachment names, counts and contents against the I/O session's
 * files, independently of image expansions. Correct text passes; missing or
 * duplicate expansion, wrong name or changed text fails. No text attachments
 * means insufficient evidence and no credit. Expander separation is checked separately.
 */
@util.annotations.Explanation("Checks text-file expansion names, counts and contents against actual attachments. Image contents are checked separately; no text attachment means no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.ATTACHMENT_CONTENT)
public class A2CLITextFileExpansionContentsMatch extends AbstractCLIArchitectureCheck {
    @Override protected CLIArchitectureChecks.Check architectureCheck() { return CLIArchitectureChecks.Check.TEXT_FILE_CONTENTS; }
}
