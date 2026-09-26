package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;

public abstract class AbstractProviderRequestCheck
		extends AbstractTraceProcessorCheck {
	protected abstract String provider();

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{
			RequestsTraced.class,
			NativeServerHandleAdapters.class,
		};
	}
}
