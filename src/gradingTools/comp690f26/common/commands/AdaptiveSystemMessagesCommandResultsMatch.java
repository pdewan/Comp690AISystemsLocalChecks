package gradingTools.comp690f26.common.commands;
/**
 * Validates response and parameter evidence preceding the selected displays.
 * A displayed history matching corrupt response/parameter data cannot earn credit.
 * Failures in later requests do not block this view. Metadata values are not compared here; metadata-only failures do not block credit.
 * Uses -system returns and the same history comparison as ordinary messages.
 * Requires two completed earlier exchanges using nonblank system instructions.
 * Attachments and numeric-setting changes are not required. Retained history
 * including those instructions passes; missing instructions or responses, or stale
 * parameters, fail. No qualifying -system view earns no credit. An ordinary-view
 * failure does not affect this check.
 */
@util.annotations.Explanation("After validating preceding response and parameter translations, checks messages -system history including previously used system instructions. Requires two completed earlier exchanges using system instructions; no qualifying -system view earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.SYSTEM_MESSAGE_RESULT)
public class AdaptiveSystemMessagesCommandResultsMatch extends AbstractMessagesCommandResultCheck {
    @Override protected eduAI.trace.processors.cli.CLIMessageView view() { return eduAI.trace.processors.cli.CLIMessageView.SYSTEM; }
}
