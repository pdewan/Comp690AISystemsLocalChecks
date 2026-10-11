package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;

import eduAI.trace.processors.*;
@Explanation("After text translation, checks earlier completed answers survive until permitted compaction.")
public class OllamaPreviousCompletedResponsesRetained extends AbstractProviderContextHistoryCheck { protected String provider(){return TraceProviderNames.OLLAMA;} protected TraceCheckResult check(TraceFilesProcessor p){return p.checkPreviousCompletedResponsesRetained(provider());} }
