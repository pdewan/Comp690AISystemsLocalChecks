package gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Rejects native SDK types on generic field paths before text and image comparisons.")
public class OllamaContextWindowTypeIntegrity
		extends AbstractProviderIndependentTypeIntegrityCheck {
	protected String provider() { return TraceProviderNames.OLLAMA; }
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{RequestsTraced.class};
	}
	protected TraceCheckResult check(BridgeDemoTraceFilesProcessor processor) {
		return processor.checkProviderIndependentContextWindowTypes(provider());
	}
}
