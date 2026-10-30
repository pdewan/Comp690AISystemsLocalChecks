package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.TraceProviderNames;
import util.annotations.Explanation;

/** Compares retained assistant messages in successive requests. Correct retention
 * requires visible translation prerequisites; lost earlier answers fail.
 * Both request contexts must be present, including across provider switches.
 * The retention comparison uses only responses followed by another request. */
@Explanation("Checks answer retention after validating both requests and the responses consumed by history.")
public class OllamaPreviousCompletedResponsesRetained extends gradingTools.comp690f26.common.AbstractCompletedResponseRetentionCheck {
    protected String provider(){return TraceProviderNames.OLLAMA;}
}
