package gradingTools.comp690f26.assignment1.contextHistorySuite;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
public abstract class AbstractProviderContextHistoryCheck extends AbstractTraceProcessorCheck {
	protected abstract String provider();
	protected Class<?>[] additionalPrecedingTests() {
		Class<?> contextMatch = TraceProviderNames.GEMINI.equals(provider())
				? GeminiTextualContextWindowMatchesGenericContextWindow.class
				: OllamaTextualContextWindowMatchesGenericContextWindow.class;
		return new Class<?>[]{RequestsTraced.class, contextMatch};
	}
}
