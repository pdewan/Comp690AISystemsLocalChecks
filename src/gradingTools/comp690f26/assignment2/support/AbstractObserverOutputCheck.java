package gradingTools.comp690f26.assignment2.support;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLISixCallChecks;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.*;

/** Delivery consumes trace evidence only; agreement additionally consumes the
 * parsed console capture. Neither independently scored dimension gates the other. */
public abstract class AbstractObserverOutputCheck extends AbstractTraceProcessorCheck {
    protected abstract boolean streaming();
    protected abstract boolean agreement();
    @Override protected Class<?>[] additionalPrecedingTests() {
        if(agreement()) return new Class<?>[]{CLIOuterOutputSyntax.class,
            streaming() ? CLIStreamDisplayedTraced.class : CLICommandDisplayedTraced.class};
        return streaming() ? new Class<?>[]{CLIStreamOutputNotifiedTraced.class,CLIStreamDisplayedTraced.class}
            : new Class<?>[]{CLICommandHandledTraced.class,CLICommandOutputNotifiedTraced.class,CLICommandDisplayedTraced.class};
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        if(!agreement()) return CLISixCallChecks.notificationDelivery(processor.getTraceRecords().values(),streaming());
        var input=getPrecedingTestInstances().stream().filter(CLIOuterOutputSyntax.class::isInstance)
            .map(CLIOuterOutputSyntax.class::cast).findFirst().orElseThrow();
        return CLISixCallChecks.outputAgreement(processor.getTraceRecords().values(),input.getInteractions().orElseThrow(),
            input.getInteractionsFile().getParent(),streaming());
    }
}
