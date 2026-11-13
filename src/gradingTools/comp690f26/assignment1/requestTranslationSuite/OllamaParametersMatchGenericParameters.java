package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaParameterStoreTypeIntegrity;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ParametersTranslatedTraced;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Checks each numeric/Duration parameter-translation value against known native
 * settings and generic-store leaves, without prescribing student keys or fields.
 * A translated 0.4 present in native temperature and generic field warmth passes;
 * a translated 0.4 absent from native settings fails. Native-only defaults are
 * allowed. Merely retaining an unsupported setting in the store is not an error,
 * but claiming to translate it without native evidence fails. Missing numeric
 * translation evidence earns no credit. Equal values cannot prove association.
 * Request context contents are not required by this comparison. */
@Explanation("Checks translated numeric/Duration values have native evidence and occur in the generic store; permits arbitrary keys and native-only defaults.")
public class OllamaParametersMatchGenericParameters
		extends AbstractProviderRequestCheck {
	protected String provider() {
		return TraceProviderNames.OLLAMA;
	}

	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericParameterMatches(provider());
	}

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			OllamaParameterStoreTypeIntegrity.class,
			ParametersTranslatedTraced.class);
	}
}
