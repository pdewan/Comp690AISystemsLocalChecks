package gradingTools.comp690f26.common._tests;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.Path;
import java.util.List;


/** Verifies explicit policy selection and failures against real assignment fixtures. */
public final class ExpectedContentConfigurationTest {
    public static void main(String[] args) throws Exception {
        Path a1Directory = Path.of(args[0]), a2Directory = Path.of(args[1]);
        var unconfigured = new NativeTraceFilesProcessor(a2Directory.resolve("CLITrace.txt"));
        try {
            unconfigured.checkExpectedPrompts();
            throw new AssertionError("Unconfigured content check was accepted");
        } catch (IllegalStateException expected) {
            require(expected.getMessage().contains("configured explicitly"), "Wrong missing-policy diagnostic");
        }
        var a1 = F26Assignment1Suite.createTraceFilesProcessor(a1Directory);
        require(a1.checkExpectedPrompts().passed(), "Correct A1 prompts rejected");
        require(a1.checkExpectedGenericImagesMatch().passed(), "Correct A1 images rejected");

        var information = new BridgeDemoTraceInformation(a1Directory);
        var invalid = new GenericTracesFileProcessor.ExpectedInputs(
                List.of("This system instruction does not occur in the submitted session."),
                List.of("This user prompt does not occur in the submitted session."),
                List.of(), new byte[]{1, 2, 3});
        var wrong = new NativeTraceFilesProcessor(information.traceFiles(), invalid);
        wrong.setExpectedContentChecks(new FixedInputContentChecks(wrong));
        require(!wrong.checkExpectedPrompts().passed(), "Wrong fixed prompts accepted");
        require(!wrong.checkExpectedGenericImagesMatch().passed(), "Wrong fixed image accepted");

        var io = F26Assignment2Suite.createInteractionsProcessor().process(a2Directory.resolve("CLIInputOutput.txt"));
        require(io.succeeded(), "A2 input could not be reconstructed");
        var a2 = F26Assignment2Suite.createTraceFilesProcessor(a2Directory);
        a2.setExpectedContentChecks(new CLIExpectedContentChecks(CLITraceExpectations.from(io.interaction().orElseThrow(), a2Directory)));
        require(a2.checkExpectedPrompts().passed(), "Correct A2 prompts rejected");
        require(a2.checkExpectedGenericImagesMatch().passed(), "Correct A2 images rejected");
        require(a2.checkExpectedRequestSettings().passed(), "Correct A2 request settings rejected");
        // Creating/configuring A2 must not replace the checker on the A1 processor.
        require(a1.checkExpectedPrompts().passed(), "A2 changed A1's content policy");
        System.out.println("ExpectedContentConfigurationTest passed: explicit policies, isolation, missing policy, wrong prompts and image");
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
