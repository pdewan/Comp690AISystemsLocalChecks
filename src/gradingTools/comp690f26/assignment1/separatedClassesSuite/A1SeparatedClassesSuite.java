package gradingTools.comp690f26.assignment1.separatedClassesSuite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Explanation("After project lookup, rejects classes assigned multiple trace-derived roles.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiServerHandleAdapterIsSeparated.class,
	OllamaServerHandleAdapterIsSeparated.class,
	GeminiServerHandleFactoryIsSeparated.class,
	OllamaServerHandleFactoryIsSeparated.class,
	GeminiPromptMessageAdapterIsSeparated.class,
	OllamaPromptMessageAdapterIsSeparated.class,
	GeminiResponseMessageAdapterIsSeparated.class,
	OllamaResponseMessageAdapterIsSeparated.class,
	GeminiParameterAdapterIsSeparated.class,
	OllamaParameterAdapterIsSeparated.class,
	GeminiResponseMetadataAdapterIsSeparated.class,
	OllamaResponseMetadataAdapterIsSeparated.class,
	GeminiChunkCallbackAdapterIsSeparated.class,
	OllamaChunkCallbackAdapterIsSeparated.class,
})
@MaxValue(70)
public class A1SeparatedClassesSuite {
}
