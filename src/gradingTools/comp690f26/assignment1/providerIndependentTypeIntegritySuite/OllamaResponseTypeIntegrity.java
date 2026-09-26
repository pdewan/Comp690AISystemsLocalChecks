package gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Rejects native SDK types on generic response paths before response-value comparisons.")
public class OllamaResponseTypeIntegrity
		extends AbstractProviderIndependentTypeIntegrityCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{ResponsesTranslatedTraced.class};
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor processor) {
		return processor.checkProviderIndependentResponseTypes(provider());
	}
}
