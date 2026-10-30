package gradingTools.comp690f26.common.commands;
/**
 * Validates response and parameter evidence preceding the selected displays.
 * A displayed history matching corrupt response/parameter data cannot earn credit.
 * Failures in later requests do not block this view. Metadata values are not compared here; metadata-only failures do not block credit.
 * Uses ordinary messages returns, actual preceding prompts, responses, attachments
 * and parameter snapshots. Requires two completed earlier exchanges using system instructions.
 * Attachments and numeric-setting changes are not required.
 * Correct retained history passes; missing responses, stale values or recognized
 * system entries fail. No qualifying ordinary view earns no credit. The -system
 * result is not assessed here; arbitrary unlabeled system entries cannot be identified.
 */
@util.annotations.Explanation("After validating preceding response and parameter translations, checks ordinary messages history and exclusion of recognized system entries. Requires two completed earlier exchanges using system instructions; no qualifying view earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.ORDINARY_MESSAGE_RESULT)
public class AdaptiveOrdinaryMessagesCommandResultsMatch extends AbstractMessagesCommandResultCheck {
    @Override protected eduAI.trace.processors.cli.CLIMessageView view() { return eduAI.trace.processors.cli.CLIMessageView.ORDINARY; }
}
