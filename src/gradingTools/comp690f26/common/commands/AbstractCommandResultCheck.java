package gradingTools.comp690f26.common.commands;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.files.*;
import java.util.List;

/** Explicit trace-only or actual-session execution of the suite's registered rules. */
public abstract class AbstractCommandResultCheck extends gradingTools.comp690f26.common.AbstractEvidenceScopedTraceCheck {
    protected abstract String commandName();
    protected abstract boolean requiresInputs();
    protected boolean selectedInput(CLIMixedIO.Entry entry) { return true; }
    protected boolean requiresPriorResponses() { return false; }
    protected boolean requiresPriorParameters() { return false; }
    protected void configureSessionRules(CLICommandResultCheckerRegistry registry,CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException { }
    @Override protected Class<?>[] additionalPrecedingTests() {
        return requiresInputs() ? new Class<?>[]{CLIOuterOutputSyntax.class} : new Class<?>[0];
    }
    /** Select consumed providers from history, then run their graded checks visibly. */
    @Override protected Class<?>[] evidencePrecedingTests(TraceFilesProcessor processor) {
        if (!requiresPriorResponses()) return new Class<?>[0];
        var inputs = getPrecedingTestInstances().stream().filter(CLIOuterOutputSyntax.class::isInstance)
            .map(CLIOuterOutputSyntax.class::cast).findFirst().orElseThrow();
        var numbers = inputs.getInteractions().orElseThrow().entries().stream()
            .filter(e -> commandName().equals(e.input().commandName()) && selectedInput(e))
            .map(CLIMixedIO.Entry::number).collect(java.util.stream.Collectors.toSet());
        var ends = new java.util.HashMap<String,Integer>();
        for (var record : processor.getTraceRecords().values())
            if (CLITraceEvidence.commandResult(record) && numbers.contains(CLITraceEvidence.number(record.getTraceLine().getAuxiliaryData())))
                ends.merge(record.getTraceFileName(),record.getTraceFileLineNumber(),Math::max);
        var prior = gradingTools.comp690f26.common.TraceEvidenceView.select(processor,
            r -> ends.containsKey(r.getTraceFileName()) && r.getTraceFileLineNumber() <= ends.get(r.getTraceFileName()));
        var required = new java.util.ArrayList<gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence>();
        required.add(gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.RESPONSE);
        required.add(gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.ACCUMULATION);
        if (requiresPriorParameters()) required.add(gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.PARAMETERS);
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.checks(prior,
            required.toArray(gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence[]::new));
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var config=FilesProvidedSupport.configuration().checks().commandChecks().orElseThrow(
            ()->new IllegalStateException("Command checks must be configured by the suite"));
        if(!requiresInputs()) return CLICommandResultChecks.checkTraces(processor.getTraceRecords().values(),config.traceRules().get(),commandName());
        for(var prerequisite:getPrecedingTestInstances()) if(prerequisite instanceof CLIOuterOutputSyntax inputs) {
            try {
                var io=inputs.getInteractions().orElseThrow();
                var directory=inputs.getInteractionsFile().toAbsolutePath().getParent();
                var registry=config.traceRules().get();
                configureSessionRules(registry,io,directory);
                return CLICommandResultChecks.check(processor.getTraceRecords().values(),io,registry,commandName(),this::selectedInput);
            } catch(java.io.IOException error) {
                return new TraceCheckResult(List.of(),List.of(),List.of("Cannot read attachment for command expectations: "+error.getMessage()));
            }
        }
        throw new IllegalStateException("Actual session prerequisite has not supplied inputs");
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
