package gradingTools.comp690f26.common.io;
/**
 * Uses only echoed setters/prompts and captured streaming lines. Both stream=true
 * and stream=false must be followed by prompts for credit. Enabled prompts with
 * chunks for each selected model pass; missing chunks, a wrong model, or chunks
 * while disabled/on a non-prompt command fail. No trace file or prescribed script
 * is required. Chunk contents are assessed by the existing delivery checks.
 */
@util.annotations.Explanation("Uses I/O only: enabled prompts need streaming output for their selected models; disabled prompts and other commands must have none. Requires prompts in both modes.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.STREAM_SETTING_CONSISTENCY)
public class AdaptiveStreamingOutputMatchesSetting extends AbstractCLIOutputCheck {
    @Override protected java.util.List<String> check(eduAI.trace.processors.cli.CLIMixedIO io,java.nio.file.Path directory) {
        return eduAI.trace.processors.cli.CLIStreamingSettingChecks.checkOutput(io);
    }
}
