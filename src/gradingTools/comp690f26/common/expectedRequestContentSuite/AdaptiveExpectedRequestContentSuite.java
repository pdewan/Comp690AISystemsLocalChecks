package gradingTools.comp690f26.common.expectedRequestContentSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


@Explanation("Assignment-specific prompts and image, gated by translation, history, and handle structure.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptiveGeminiExpectedPromptsInProviderRequests.class,
    AdaptiveOllamaExpectedPromptsInProviderRequests.class,
    AdaptiveGeminiExpectedImageMatchesGenericImage.class,
    AdaptiveOllamaExpectedImageMatchesGenericImage.class
})
public class AdaptiveExpectedRequestContentSuite {
}
