package gradingTools.comp690f26.assignment1.streamingBehaviorSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import util.annotations.Explanation;

@Explanation("Distinguishes chunk delivery, running accumulation, and reuse of the completed answer.")
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
		GeminiPartialResponseCallbackInvoked.class,
		OllamaPartialResponseCallbackInvoked.class,
		GeminiStreamingChunksAccumulatedCorrectly.class,
		OllamaStreamingChunksAccumulatedCorrectly.class,
		GeminiMergedStreamingResponseInNextContextWindow.class,
		OllamaMergedStreamingResponseInNextContextWindow.class,
		 })
//@MaxValue(30)

public class A1StreamingBehaviorSuite {
}
