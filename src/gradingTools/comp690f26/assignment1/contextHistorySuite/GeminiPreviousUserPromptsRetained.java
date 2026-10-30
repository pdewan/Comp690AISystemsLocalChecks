package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.*;
import util.annotations.Explanation;

/** Requires valid contexts/translations at both endpoints of each comparison.
 * Missing earlier context across a provider switch fails; a broken request
 * outside the compared pairs preserves credit. Missing retention evidence fails. */
@Explanation("Checks retained history after validating both compared requests, including provider switches.")
public class GeminiPreviousUserPromptsRetained extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.GEMINI;} protected TraceCheckResult checkHistory(TraceFilesProcessor p){return p.checkPreviousUserPromptsRetained(provider());} }
