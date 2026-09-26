package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Checks translated response values and metadata keys against their native sources.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiResponseMatchesGenericResponse.class,
	OllamaResponseMatchesGenericResponse.class,
	GeminiMetadataMatchesGenericMetadata.class,
	OllamaMetadataMatchesGenericMetadata.class
})
@MaxValue(60)
public class A1ResponseTranslationSuite {
}
