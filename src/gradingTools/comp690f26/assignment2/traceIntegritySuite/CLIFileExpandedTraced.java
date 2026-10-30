package gradingTools.comp690f26.assignment2.traceIntegritySuite;

import eduAI.trace.cli.MinimalCLITrace.Event;

/**
 * Requires the named CLI event and resolves its participating classes in the project.
 * Present evidence with resolvable traced classes passes; a missing event fails.
 * A missing participating class blocks this check; unrelated roles do not.
 * Event presence does not prove semantic correctness; consistency checks do that.
 */
@util.annotations.Explanation("Resolves participating classes and requires file_expanded evidence.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.TRACE_EVENT)
public class CLIFileExpandedTraced extends AbstractA2TraceEventTraced {
    @Override protected String eventName() { return Event.FILE_EXPANDED.eventName(); }
}
