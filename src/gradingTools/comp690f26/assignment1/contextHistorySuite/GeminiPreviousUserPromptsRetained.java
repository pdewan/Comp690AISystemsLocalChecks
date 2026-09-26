package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;

import eduAI.trace.processors.*;
@Explanation("After text translation, tracks earlier user prompts; gates expected-content checks.")
public class GeminiPreviousUserPromptsRetained extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.GEMINI;} protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p){return p.checkPreviousUserPromptsRetained(provider());} }
