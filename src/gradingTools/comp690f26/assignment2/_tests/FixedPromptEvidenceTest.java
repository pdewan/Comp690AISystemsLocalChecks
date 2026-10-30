package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.*;
import java.nio.file.Path;
import java.util.List;


/** Uses explicit correct fixtures. Correct fixed prompts pass; a required prompt
 * absent from those same requests fails independently of retention grading. */
public final class FixedPromptEvidenceTest {
    private static final String ABSENT_PROMPT = "Required prompt deliberately absent in this regression fixture";
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Supply the A1 correct directory");
        var info = new BridgeDemoTraceInformation(Path.of(args[0]));
        var expected = info.expectedInputs();
        for (boolean missing : List.of(false, true)) {
            var inputs = missing ? new GenericTracesFileProcessor.ExpectedInputs(
                expected.systemPromptTexts(), List.of(ABSENT_PROMPT),
                expected.compactionPromptTexts(), expected.expectedImageBytes()) : expected;
            var processor = new NativeTraceFilesProcessor(info.traceFiles(), inputs);
            processor.setExpectedContentChecks(new eduAI.trace.processors.assignment1.BridgeDemoContentChecks(processor));
            processor.processTraceFiles();
            for (String provider : List.of(TraceProviderNames.GEMINI, TraceProviderNames.OLLAMA)) {
                if (processor.checkExpectedPrompts(provider).passed() == missing)
                    throw new AssertionError("Incorrect fixed-prompt result for " + provider + ", missing=" + missing);
            }
        }
        System.out.println("FixedPromptEvidenceTest passed: correct and absent prompts for both providers");
    }
}
