package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.common.commands.AdaptiveExpectedCommandInputs;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedImageMatchesGenericImage;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedPromptsInProviderRequests;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedImageMatchesGenericImage;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedPromptsInProviderRequests;
import gradingTools.comp690f26.common.properties.AdaptiveExpectedPropertyInputs;
import gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 ExpectedContent checks with their existing points and prerequisites.")
@MaxValue(120)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptiveGeminiExpectedPromptsInProviderRequests.class,
    AdaptiveOllamaExpectedPromptsInProviderRequests.class,
    AdaptiveGeminiExpectedImageMatchesGenericImage.class,
    AdaptiveOllamaExpectedImageMatchesGenericImage.class,
    A2CLIRequestExpectationsProvided.class,
    AdaptiveExpectedPropertyInputs.class,
    AdaptiveExpectedCommandInputs.class
})
public class A1A2ExpectedContentSuite { }
