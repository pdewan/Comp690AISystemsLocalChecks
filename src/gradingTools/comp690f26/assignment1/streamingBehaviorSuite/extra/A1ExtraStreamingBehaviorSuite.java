package gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiMergedStreamingResponseInNextContextWindow;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiStreamingChunksAccumulatedCorrectly;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaMergedStreamingResponseInNextContextWindow;
import util.annotations.Explanation;
import util.annotations.IsExtra;
import util.annotations.MaxValue;

@Explanation("After partial-callback checks, verifies the optional final callback receives the merged answer.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	GeminiCompleteResponseCallbackInvokedWithMergedResponse.class,
	OllamaCompleteResponseCallbackInvokedWithMergedResponse.class
})
@MaxValue(30)
@IsExtra(true)

public class A1ExtraStreamingBehaviorSuite {
}
