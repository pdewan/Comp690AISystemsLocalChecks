package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;

import eduAI.trace.processors.*;
@Explanation("After text translation, tracks earlier system instructions; gates expected-content checks.")
public class OllamaPreviousSystemPromptsRetained extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.OLLAMA;} protected TraceCheckResult check(BridgeDemoTraceFilesProcessor p){return p.checkPreviousSystemPromptsRetained(provider());} }
