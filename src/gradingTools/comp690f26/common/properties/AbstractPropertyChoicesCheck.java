package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIPropertyChoicesChecks;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.files.FilesProvidedSupport;

/** Supplies explicit suite contracts to required-choice checks without an I/O prerequisite. */
public abstract class AbstractPropertyChoicesCheck extends AbstractTraceProcessorCheck {
    protected abstract CLIPropertyChoicesChecks.Check rule();
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var policy=FilesProvidedSupport.configuration().checks().propertyExpectations()
            .orElseThrow(()->new IllegalStateException("Property choice requirements must be configured by the suite"));
        return CLIPropertyChoicesChecks.check(rule(),processor.getTraceRecords().values(),policy);
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
