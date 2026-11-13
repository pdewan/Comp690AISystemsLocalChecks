package gradingTools.comp690f26.common.io;
/** A successful transcript with numbered entries and @@[model] response labels passes.
 * A saved answer beginning with [model] fails for the missing marker. No successful
 * transcript earns no credit. Model choice, answer text and order are graded separately. */
@util.annotations.Explanation("Requires a successful transcript with numbered entries and @@[model] labels on saved answers.")
public class CLITranscriptOutputSyntax extends AbstractCLIOutputCheck {
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) {
        return eduAI.trace.processors.cli.CLIOutputSyntax.check(eduAI.trace.processors.cli.CLIOutputSyntax.Kind.TRANSCRIPT,io);
    }
}
