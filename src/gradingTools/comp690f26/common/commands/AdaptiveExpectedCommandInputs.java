package gradingTools.comp690f26.common.commands;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.files.*;
import java.util.List;

/**
 * First runs command-result consistency checks. Registration and handler reuse
 * are separate assessments and do not gate input credit. Then
 * compares actual input count/order, specified options and attachment names with
 * explicit suite policy, and matches every input number/role to a traced return.
 * Property-specific values and changes are checked by AdaptiveExpectedPropertyInputs.
 * Success: the configured sequence is followed with freely chosen prompt wording.
 * Failure: a required messages -system command is omitted, an attachment is wrong,
 * or internally consistent traces describe another command than the user entered.
 * Suite-configured request-content checks also validate the prompts and images
 * sent to providers, including their A1 translation and history prerequisites.
 * Suite-configured consistency prerequisites also run before input validation.
 * Failed consistency prerequisites block this check; they never depend on it.
 */
@util.annotations.Explanation("Also runs suite-configured request-content, file-content and output-delivery checks. First runs command consistency checks, then compares actual command order, options and attachments with suite policy and matches inputs to traced returns. Free prompt wording is allowed. Property requirements are checked separately.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REQUIRED_INPUT)
public class AdaptiveExpectedCommandInputs extends gradingTools.comp690f26.common.AbstractTraceProcessorCheck {
    @Override protected Class<?>[] additionalPrecedingTests() {
        return gradingTools.comp690f26.common.PrecedingTestUtils.add(new Class<?>[]{CLIOuterOutputSyntax.class,
            AdaptivePropertyCommandResultsMatch.class,AdaptivePromptCommandResultsMatch.class,
            AdaptiveTranscriptCommandResultsMatch.class,AdaptiveOrdinaryMessagesCommandResultsMatch.class,AdaptiveSystemMessagesCommandResultsMatch.class}, FilesProvidedSupport.configuration().checks().commandInputPrerequisites().toArray(Class<?>[]::new));
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var config=FilesProvidedSupport.configuration().checks().commandChecks().orElseThrow(
            ()->new IllegalStateException("Required command inputs must be configured by the suite"));
        for(var prerequisite:getPrecedingTestInstances()) if(prerequisite instanceof CLIOuterOutputSyntax inputs)
            return config.expectedInputs().check(processor.getTraceRecords().values(),inputs.getInteractions().orElseThrow());
        throw new IllegalStateException("Actual session prerequisite has not supplied inputs");
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
