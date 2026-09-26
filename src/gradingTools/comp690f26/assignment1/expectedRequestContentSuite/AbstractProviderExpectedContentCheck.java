package gradingTools.comp690f26.assignment1.expectedRequestContentSuite;

import java.util.ArrayList;
import java.util.List;

import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.PrecedingTestUtils;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiExpectedContextWindowCompaction;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousUserPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaExpectedContextWindowCompaction;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousUserPromptsRetained;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.GeminiServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.OllamaServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.AbstractProviderRequestCheck;

public abstract class AbstractProviderExpectedContentCheck
		extends AbstractProviderRequestCheck {
	@Override
	protected Class<?>[] additionalPrecedingTests() {
		List<Class<?>> continuityTests = new ArrayList<>();
		if (TraceProviderNames.GEMINI.equals(provider())) {
			continuityTests.add(GeminiServerHandleAdapterHasRequiredTypes.class);
			continuityTests.add(GeminiPreviousSystemPromptsRetained.class);
			continuityTests.add(GeminiPreviousUserPromptsRetained.class);
			continuityTests.add(GeminiPreviousImagesRetained.class);
			continuityTests.add(GeminiPreviousCompletedResponsesRetained.class);
			continuityTests.add(GeminiExpectedContextWindowCompaction.class);
		} else {
			continuityTests.add(OllamaServerHandleAdapterHasRequiredTypes.class);
			continuityTests.add(OllamaPreviousSystemPromptsRetained.class);
			continuityTests.add(OllamaPreviousUserPromptsRetained.class);
			continuityTests.add(OllamaPreviousImagesRetained.class);
			continuityTests.add(OllamaPreviousCompletedResponsesRetained.class);
			continuityTests.add(OllamaExpectedContextWindowCompaction.class);
		}
		return PrecedingTestUtils.add(
				super.additionalPrecedingTests(),
				continuityTests.toArray(Class<?>[]::new));
	}
}
