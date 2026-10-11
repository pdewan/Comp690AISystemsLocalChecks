package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;

import eduAI.trace.processors.*;
@Explanation("After text translation, tracks earlier user prompts; gates expected-content checks.")
public class OllamaPreviousUserPromptsRetained extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.OLLAMA;} protected TraceCheckResult check(TraceFilesProcessor p){return p.checkPreviousUserPromptsRetained(provider());} }
