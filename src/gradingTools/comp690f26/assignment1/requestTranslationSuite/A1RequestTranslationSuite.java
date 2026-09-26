package gradingTools.comp690f26.assignment1.requestTranslationSuite;
import util.annotations.Explanation;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@Explanation("Checks generic text, image summaries, and parameters against native request evidence.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiTextualContextWindowMatchesGenericContextWindow.class,
	OllamaTextualContextWindowMatchesGenericContextWindow.class,
	GeminiImageMatchesGenericImage.class,
	OllamaImageMatchesGenericImage.class,
	GeminiParametersMatchGenericParameters.class,
	OllamaParametersMatchGenericParameters.class
})
public class A1RequestTranslationSuite {}
