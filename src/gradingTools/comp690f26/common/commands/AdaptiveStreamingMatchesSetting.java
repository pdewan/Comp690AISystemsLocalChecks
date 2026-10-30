package gradingTools.comp690f26.common.commands;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIStreamingSettingChecks;
import gradingTools.comp690f26.common.AbstractEvidenceScopedTraceCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import java.util.List;


/**
 * Uses actual setters, prompt inputs, command returns, partial callbacks,
 * streaming notifications/displays and captured chunks. Both enabled and disabled
 * prompts are required for credit. A prompt after stream=true with callbacks and
 * output for its selected models passes; missing output or streaming after
 * stream=false fails. No particular script, model name or prompt text is required.
 * Command returns delimit callback intervals; detailed callback identity and
 * content matching are required first through the A1 callback prerequisites.
 * For example, a callback carrying an accumulated answer fails before this check
 * can award points, even if its command has streaming enabled.
 */
@util.annotations.Explanation("After A1 callback-correctness checks, requires prompts with streaming enabled and disabled. Each enabled prompt must produce callbacks and notified, displayed and captured chunks for its selected models; disabled prompts and other commands must produce no streaming output.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.STREAM_SETTING_CONSISTENCY)
public class AdaptiveStreamingMatchesSetting extends AbstractEvidenceScopedTraceCheck {
    @Override protected Class<?>[] additionalPrecedingTests() { return new Class<?>[]{CLIOuterOutputSyntax.class}; }
    @Override protected Class<?>[] evidencePrecedingTests(TraceFilesProcessor processor) {
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.checks(processor,
            gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.CALLBACK);
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        for(var prerequisite:getPrecedingTestInstances()) if(prerequisite instanceof CLIOuterOutputSyntax syntax) {
            var errors=CLIStreamingSettingChecks.check(processor.getTraceRecords().values(),syntax.getInteractions().orElseThrow());
            return new TraceCheckResult(List.of(),List.of(),errors);
        }
        throw new IllegalStateException("CLI outer syntax prerequisite did not run");
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
