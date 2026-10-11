package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;

import eduAI.trace.processors.*;
@Explanation("After text translation, verifies history is reduced at expected turns; gates expected content.")
public class GeminiExpectedContextWindowCompaction extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.GEMINI;} protected TraceCheckResult check(TraceFilesProcessor p){return p.checkExpectedContextWindowCompactions(provider());} }
