package gradingTools.comp690f26.common.commands;
/**
 * Compares returning handler identities for repeated occurrences of each observed command. A command seen once provides no reuse comparison.
 * Success: two prop returns use the same object, even if no transcript command occurs. Failure: a later prop return uses a new object, including another instance of the same class. No repeated command means insufficient evidence and no credit. Required command coverage is checked separately.
 */
@util.annotations.Explanation("Checks that repeated occurrences of each observed command use the same handler object. No repeated command means insufficient evidence and no credit; it does not require every assignment command.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REGISTRATION)
public class CommandHandlerInstancesReused extends gradingTools.comp690f26.common.AbstractTraceProcessorCheck {
    @Override protected eduAI.trace.processors.TraceCheckResult check(eduAI.trace.processors.TraceFilesProcessor processor) {
        return eduAI.trace.processors.cli.CLICommandHandlerChecks.check(processor.getTraceRecords().values(),
            eduAI.trace.processors.cli.CLICommandHandlerChecks.Check.REUSE);
    }
    @Override protected String shortFailureMessage(eduAI.trace.processors.TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
