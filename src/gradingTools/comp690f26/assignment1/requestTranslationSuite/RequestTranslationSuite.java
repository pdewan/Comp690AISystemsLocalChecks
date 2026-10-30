package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Six provider translation checks share 120 points equally: 20 per check.
 * Successful text, image or parameter translation earns its own allocation;
 * failed or missing evidence earns no credit for the affected check. */
@MaxValue(120)
@Explanation("Compares native and generic request text, images and parameters; 20 points per check.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiTextualContextWindowMatchesGenericContextWindow.class,
    OllamaTextualContextWindowMatchesGenericContextWindow.class,
    GeminiImageMatchesGenericImage.class,
    OllamaImageMatchesGenericImage.class,
    GeminiParametersMatchGenericParameters.class,
    OllamaParametersMatchGenericParameters.class
})
public class RequestTranslationSuite {}
