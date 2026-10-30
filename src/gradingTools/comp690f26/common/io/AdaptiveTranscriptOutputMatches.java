package gradingTools.comp690f26.common.io;
/** Checks exact transcript entry counts/order and earlier captured inputs, setter confirmations and answers. Requires two exchanges.
 * Correct printed history passes without any trace file. Wrong values, missing,
 * duplicate or reordered entries fail; insufficient exercise earns no credit.
 * Expected values come from this session, not the required assignment script. */
@util.annotations.Explanation("Checks exact transcript entry counts/order and earlier captured inputs, setter confirmations and answers. Requires two exchanges.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMAND_RESULT)
public class AdaptiveTranscriptOutputMatches extends AbstractCLIOutputCheck {
    @Override protected Class[] precedingTests() { return new Class[]{CLIOuterOutputSyntax.class,CLITranscriptOutputSyntax.class}; }
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException {
        return eduAI.trace.processors.cli.CLIInputOutputChecks.check(eduAI.trace.processors.cli.CLIInputOutputChecks.Check.TRANSCRIPT,io,directory,gradingTools.comp690f26.files.FilesProvidedSupport.configuration().checks().attachmentKinds());
    }
}
