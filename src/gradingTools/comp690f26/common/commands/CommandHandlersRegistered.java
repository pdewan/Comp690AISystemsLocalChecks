package gradingTools.comp690f26.common.commands;
/**
 * Checks each observed command return against its registry. Named command keys must map to the returned handler identity; an unnamed prompt requires membership without a prescribed key. All observations in a trace file must use one registry and dispatcher.
 * Success: prop maps to the returning property handler, and an unnamed prompt handler occurs under a student-chosen key. Failure: the key maps to another object, the registry changes, or identities are absent. No command returns means insufficient evidence and no credit. This does not require the assignment command sequence.
 */
@util.annotations.Explanation("Checks observed command-to-handler registry mappings and stable registry/dispatcher identities. Unnamed prompts require handler membership without a fixed key. Missing evidence earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REGISTRATION)
public class CommandHandlersRegistered extends gradingTools.comp690f26.common.AbstractTraceProcessorCheck {
    @Override protected eduAI.trace.processors.TraceCheckResult check(eduAI.trace.processors.TraceFilesProcessor processor) {
        return eduAI.trace.processors.cli.CLICommandHandlerChecks.check(processor.getTraceRecords().values(),
            eduAI.trace.processors.cli.CLICommandHandlerChecks.Check.REGISTRATION);
    }
    @Override protected String shortFailureMessage(eduAI.trace.processors.TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
