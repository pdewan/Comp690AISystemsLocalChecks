package gradingTools.comp690f26.assignment1.contextHistorySuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Image continuity uses image evidence only. Bad text or responses do not
 * veto retained images; missing endpoint contexts, changed images or invalid image
 * translation fail, including at provider switches. */
@Explanation("Checks image retention using both requests; unrelated text and response failures do not block it.")
@MaxValue(10)
public class GeminiPreviousImagesRetained
		extends AbstractProviderContextHistoryCheck {
	protected String provider() { return TraceProviderNames.GEMINI; }
	protected gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence requestComparison() {
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.IMAGES;
    }
	protected TraceCheckResult checkHistory(TraceFilesProcessor processor) {
		return processor.checkPreviousImagesRetained(provider());
	}
}
