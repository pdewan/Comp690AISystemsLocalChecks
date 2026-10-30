package gradingTools.comp690f26.common.translationSuite;

import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiContextWindowTypeIntegrity;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiParameterStoreTypeIntegrity;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiResponseTypeIntegrity;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaContextWindowTypeIntegrity;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaParameterStoreTypeIntegrity;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaResponseTypeIntegrity;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiImageMatchesGenericImage;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaImageMatchesGenericImage;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiMetadataMatchesGenericMetadata;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaMetadataMatchesGenericMetadata;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Compares native and generic request text, images, parameters, responses and
 * metadata. Matching evidence passes; missing or inconsistent evidence fails.
 * Response checks retain 15 points each; the remaining 120 points are shared
 * equally by the six request checks. Six zero-point type-integrity checks
 * reject native SDK types in generic data before dependent value comparisons.
 * This grouping adds no prerequisites. */
@Explanation("Checks generic type integrity and request/response translation, including parameters and metadata.")
@MaxValue(180)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiContextWindowTypeIntegrity.class,
    OllamaContextWindowTypeIntegrity.class,
    GeminiParameterStoreTypeIntegrity.class,
    OllamaParameterStoreTypeIntegrity.class,
    GeminiResponseTypeIntegrity.class,
    OllamaResponseTypeIntegrity.class,
    GeminiTextualContextWindowMatchesGenericContextWindow.class,
    OllamaTextualContextWindowMatchesGenericContextWindow.class,
    GeminiImageMatchesGenericImage.class,
    OllamaImageMatchesGenericImage.class,
    GeminiParametersMatchGenericParameters.class,
    OllamaParametersMatchGenericParameters.class,
    GeminiResponseMatchesGenericResponse.class,
    OllamaResponseMatchesGenericResponse.class,
    GeminiMetadataMatchesGenericMetadata.class,
    OllamaMetadataMatchesGenericMetadata.class
})
public class TranslationSuite { }
