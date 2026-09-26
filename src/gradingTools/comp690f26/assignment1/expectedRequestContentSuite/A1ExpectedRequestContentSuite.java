package gradingTools.comp690f26.assignment1.expectedRequestContentSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import util.annotations.Explanation;

@Explanation("Assignment-specific prompts and image, gated by translation, history, and handle structure.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiExpectedPromptsInProviderRequests.class,
	OllamaExpectedPromptsInProviderRequests.class,
	GeminiExpectedImageMatchesGenericImage.class,
	OllamaExpectedImageMatchesGenericImage.class
})
public class A1ExpectedRequestContentSuite {
}
