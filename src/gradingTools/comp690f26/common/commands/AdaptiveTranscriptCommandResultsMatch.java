package gradingTools.comp690f26.common.commands;
/**
 * Validates underlying A1 responses and streaming accumulation preceding selected
 * displays. A wrong prompt-handler result does not veto correctly retained model
 * responses; corrupt model-response evidence still fails.
 * Does not load attachments or prepare message-view expectations; missing attachment
 * files cannot block transcript credit.
 * Builds expectations from actual earlier inputs and traced returns in the session, not from a prescribed assignment script. Checks earlier input text, prompt responses and setter confirmations; excludes the executing transcript command.
 * Success: a different valid session retains its own inputs and outputs. Failure: an earlier input/response is missing, a setter confirmation is lost, or the current transcript command appears in its own result. No transcript or fewer than two completed earlier exchanges means no credit. Unsupported options are reported as unavailable evidence. Exact input text requires the I/O log.
 */
@util.annotations.Explanation("After validating preceding model responses, compares transcript history with earlier inputs, answers and setter confirmations. Wrong prompt-handler results do not veto correct history. Requires two completed exchanges; missing history or self-inclusion fails. No attachment files are required.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMAND_RESULT)
public class AdaptiveTranscriptCommandResultsMatch extends AbstractCommandResultCheck {
    @Override protected boolean requiresPriorResponses() { return true; }
    // A transcript without two preceding completed prompt/response exchanges cannot earn credit.
    @Override protected String commandName() { return eduAI.trace.processors.cli.CLICommandNames.TRANSCRIPT; }
    @Override protected boolean requiresInputs() { return true; }
}
