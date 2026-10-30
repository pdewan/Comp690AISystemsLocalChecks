package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.OllamaCompleteResponseCallbackInvokedWithMergedResponse;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/** Runs regular streaming behavior and optional completion callbacks.
 * Completion tests carry their own extra-credit flags and 15-point weights;
 * selecting this suite exercises them without making them regular prerequisites. */
@Explanation("Checks streaming delivery, accumulation, history and extra-credit completion callbacks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiPartialResponseCallbackInvoked.class,
    OllamaPartialResponseCallbackInvoked.class,
    GeminiStreamingChunksAccumulatedCorrectly.class,
    OllamaStreamingChunksAccumulatedCorrectly.class,
    GeminiMergedStreamingResponseInNextContextWindow.class,
    OllamaMergedStreamingResponseInNextContextWindow.class,
    GeminiCompleteResponseCallbackInvokedWithMergedResponse.class,
    OllamaCompleteResponseCallbackInvokedWithMergedResponse.class
})

public class StreamingBehaviorSuite {
}
