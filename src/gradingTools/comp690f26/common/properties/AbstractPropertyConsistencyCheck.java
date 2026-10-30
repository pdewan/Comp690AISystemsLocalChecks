package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.files.FilesProvidedSupport;

/**
 * Supplies configured property type contracts to a trace-only consistency rule.
 * Concrete tests document their own evidence requirements and success/failure
 * examples. No I/O prerequisite is added here. A suite with explicit contracts
 * can run these checks on another session; missing configuration is an error.
 */
public abstract class AbstractPropertyConsistencyCheck extends AbstractTraceProcessorCheck {
    protected abstract CLIPropertyConsistencyChecks.Check rule();
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var policy=FilesProvidedSupport.configuration().checks().propertyExpectations()
            .orElseThrow(()->new IllegalStateException("Property contracts must be configured by the suite"));
        return CLIPropertyConsistencyChecks.check(rule(),processor.getTraceRecords().values(),
            CLIPropertyCheckerRegistry.fromDescriptors(policy.descriptors()));
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
