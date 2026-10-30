package gradingTools.comp690f26.common.io;
/** Checks printed setter/getter names, values and sources against actual inputs. Requires a setter followed by its getter.
 * Correct printed history passes without any trace file. Wrong values, missing,
 * duplicate or reordered entries fail; insufficient exercise earns no credit.
 * Expected values come from this session, not the required assignment script. */
@util.annotations.Explanation("Checks printed setter/getter names, values and sources against actual inputs. Requires a setter followed by its getter.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMAND_RESULT_CONTAINMENT)
public class AdaptivePropertyOutputMatches extends AbstractCLIOutputCheck {
    @Override protected Class[] precedingTests() { return new Class[]{CLIOuterOutputSyntax.class,CLIPropertyOutputSyntax.class}; }
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException {
        return eduAI.trace.processors.cli.CLIInputOutputChecks.check(eduAI.trace.processors.cli.CLIInputOutputChecks.Check.PROPERTIES,io,directory,gradingTools.comp690f26.files.FilesProvidedSupport.configuration().checks().attachmentKinds());
    }
}
