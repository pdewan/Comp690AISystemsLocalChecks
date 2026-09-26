package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import util.annotations.Explanation;
import util.annotations.MaxValue;
import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ParametersTranslatedTraced;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiParameterStoreTypeIntegrity;

@Explanation("After trace/type/interface checks, validates native settings and generic store keys/values.")
@MaxValue(20)
public class GeminiParametersMatchGenericParameters
		extends AbstractProviderRequestCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericParameterMatches(provider());
	}

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			GeminiParameterStoreTypeIntegrity.class,
			ParametersTranslatedTraced.class,
			NativeParameterAdapters.class);
	}
}
