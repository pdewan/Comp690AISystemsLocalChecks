package gradingTools.comp690f26.common.io;
/** Checks successful transcript bodies against the CLI grammar. Proper labels and
 * delimiters pass; missing labels, malformed fields or absent exercise fail.
 * Values/counts/history agreement belong to the separate consistency checks. */
@util.annotations.Explanation("Checks transcript output-body syntax; requires a successful example and rejects malformed fields or labels.")
public class CLITranscriptOutputSyntax extends AbstractCLIOutputCheck {
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) {
        return eduAI.trace.processors.cli.CLIOutputSyntax.check(eduAI.trace.processors.cli.CLIOutputSyntax.Kind.TRANSCRIPT,io);
    }
}
