package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.*;
import util.annotations.Explanation;

@Explanation("After text translation, verifies history is reduced at expected turns; gates expected content.")
public class A1OllamaExpectedContextWindowCompaction extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.OLLAMA;} protected TraceCheckResult checkHistory(TraceFilesProcessor p){return p.checkExpectedContextWindowCompactions(provider());} }
