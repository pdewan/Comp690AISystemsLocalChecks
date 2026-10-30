package gradingTools.comp690f26.common.contextHistorySuite;

import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousUserPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousUserPromptsRetained;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/**
 * Shares the eight retention checks between A1 and A2, at ten points each.
 * Requests retaining prior system/user prompts, images and completed responses
 * pass; missing or changed retained content fails. Missing comparison evidence
 * earns no credit under the leaf checks. Compaction is assessed separately in A1.
 */
@Explanation("Checks retained prompts, images and responses for both providers; eight checks at 10 points each.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiPreviousSystemPromptsRetained.class,
    OllamaPreviousSystemPromptsRetained.class,
    GeminiPreviousUserPromptsRetained.class,
    OllamaPreviousUserPromptsRetained.class,
    GeminiPreviousImagesRetained.class,
    OllamaPreviousImagesRetained.class,
    GeminiPreviousCompletedResponsesRetained.class,
    OllamaPreviousCompletedResponsesRetained.class
})
@MaxValue(ContextHistorySuite.POINTS)
public class ContextHistorySuite {
    public static final int POINTS = 80;
}
