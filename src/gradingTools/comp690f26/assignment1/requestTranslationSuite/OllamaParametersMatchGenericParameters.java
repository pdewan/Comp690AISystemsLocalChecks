package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaParameterStoreTypeIntegrity;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ParametersTranslatedTraced;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Compares generic parameters with native settings. Missing context fields do not block credit;
 * incorrect parameter values, missing translations or wrong values fail. */
@Explanation("Checks native/generic parameters independently of missing request context fields.")
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
