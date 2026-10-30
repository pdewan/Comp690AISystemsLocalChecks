package gradingTools.comp690f26.assignment1.separatedClassesSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After project lookup, rejects classes assigned multiple trace-derived roles.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    ServerHandleFactoryRegistryIsSeparated.class,
    GenericContextWindowMessageIsSeparated.class,
    ParameterStoreIsSeparated.class,
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
// The framework's suite maximum includes the three individually scored extras:
// 70 regular points (14 checks) plus 15 extra-credit points (3 checks).
@MaxValue(85)
public class A1SeparatedClassesSuite {
}
