package gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Rejects native SDK types inside generic dumps before comparing their values.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiContextWindowTypeIntegrity.class,
	OllamaContextWindowTypeIntegrity.class,
	GeminiParameterStoreTypeIntegrity.class,
	OllamaParameterStoreTypeIntegrity.class,
	GeminiResponseTypeIntegrity.class,
	OllamaResponseTypeIntegrity.class
})
@MaxValue(0)
public class A1ProviderIndependentTypeIntegritySuite {
}
