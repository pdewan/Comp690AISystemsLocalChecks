package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import util.annotations.Explanation;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMetadataAdapters;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.MetadataTranslatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;

@Explanation("After trace/interface checks, maps each metadata key to its native value and normalizes units.")
public class GeminiMetadataMatchesGenericMetadata
		extends AbstractProviderResponseCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericMetadataMatches(provider());
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			ResponsesTranslatedTraced.class,
			MetadataTranslatedTraced.class,
			NativeResponseMetadataAdapters.class);
	}
}
