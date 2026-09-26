package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters;

public abstract class AbstractProviderResponseCheck
		extends AbstractTraceProcessorCheck {
	protected abstract String provider();

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{NativeServerHandleAdapters.class};
	}
}
