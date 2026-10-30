package gradingTools.comp690f26.common.io;
/** Checks system-inclusive message counts, roles, instructions, inputs, answers and parameter values. Requires two exchanges.
 * Correct printed history passes without any trace file. Wrong values, missing,
 * duplicate or reordered entries fail; insufficient exercise earns no credit.
 * Checks syntax within this selected view using the shared message parser.
 * A malformed other view does not block this view; malformed selected output fails.
 * Expected values come from this session, not the required assignment script. */
@util.annotations.Explanation("Checks system-inclusive message counts, roles, instructions, inputs, answers and parameter values. Requires two exchanges; checks this view's syntax independently of the other view.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.SYSTEM_MESSAGE_RESULT)
public class AdaptiveSystemMessagesOutputMatches extends AbstractCLIOutputCheck {
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException {
        return eduAI.trace.processors.cli.CLIInputOutputChecks.check(eduAI.trace.processors.cli.CLIInputOutputChecks.Check.SYSTEM_MESSAGES,io,directory,gradingTools.comp690f26.files.FilesProvidedSupport.configuration().checks().attachmentKinds());
    }
}
