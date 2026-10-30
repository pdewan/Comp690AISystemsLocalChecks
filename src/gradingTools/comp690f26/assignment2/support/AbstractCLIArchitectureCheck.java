package gradingTools.comp690f26.assignment2.support;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.*;
import gradingTools.comp690f26.common.AbstractEvidenceScopedTraceCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import java.util.List;


public abstract class AbstractCLIArchitectureCheck extends AbstractEvidenceScopedTraceCheck {
    protected abstract CLIArchitectureChecks.Check architectureCheck();
    @Override protected Class<?>[] additionalPrecedingTests() { Class<?>[] events=switch(architectureCheck()) {
        case HANDLERS -> new Class<?>[]{CLICommandHandledTraced.class,CLIPropertyHandledTraced.class};
        case MODELS, STORE -> new Class<?>[]{CLIPropertyHandledTraced.class};
        case COMMAND_OUTPUT -> new Class<?>[]{CLICommandHandledTraced.class,CLICommandOutputNotifiedTraced.class,CLICommandDisplayedTraced.class};
        case STREAM_OUTPUT -> new Class<?>[]{CLIStreamOutputNotifiedTraced.class,CLIStreamDisplayedTraced.class};
        case FILE_CONTENTS, TEXT_FILE_CONTENTS, IMAGE_FILE_CONTENTS, FILE_SEPARATION -> new Class<?>[]{CLIFileExpandedTraced.class};
        case HISTORY -> new Class<?>[]{CLIPropertyHandledTraced.class,CLIParameterHistoryUpdatedTraced.class};
    };
    return gradingTools.comp690f26.common.PrecedingTestUtils.add(events,CLIOuterOutputSyntax.class); }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        for(var prerequisite:getPrecedingTestInstances()) {
            if(prerequisite instanceof CLIOuterOutputSyntax input && input.getInteractions().isPresent()) {
                if(architectureCheck()==CLIArchitectureChecks.Check.TEXT_FILE_CONTENTS || architectureCheck()==CLIArchitectureChecks.Check.IMAGE_FILE_CONTENTS)
                    return eduAI.trace.processors.cli.CLIFileExpansionContentsChecks.check(
                        architectureCheck()==CLIArchitectureChecks.Check.TEXT_FILE_CONTENTS ? eduAI.trace.processors.cli.CLIFileExpansionContentsChecks.Kind.TEXT : eduAI.trace.processors.cli.CLIFileExpansionContentsChecks.Kind.IMAGE,
                        processor.getTraceRecords().values(),input.getInteractions().orElseThrow(),input.getInteractionsFile().getParent(),
                        gradingTools.comp690f26.files.FilesProvidedSupport.configuration().checks().attachmentKinds());
                return CLIArchitectureChecks.check(architectureCheck(), processor.getTraceRecords().values(),
                    input.getInteractions().orElseThrow(), input.getInteractionsFile().getParent());
            }
        }
        return new TraceCheckResult(List.of(),List.of(),List.of("CLI input expectations unavailable."));
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
