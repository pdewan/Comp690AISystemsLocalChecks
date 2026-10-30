package gradingTools.comp690f26.common.expectedRequestContentSuite;

import gradingTools.comp690f26.assignment1.requestTranslationSuite.AbstractProviderRequestCheck;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import gradingTools.comp690f26.files.FilesProvidedSupport;

public abstract class AbstractAdaptiveProviderExpectedContentCheck extends AbstractProviderRequestCheck {
    protected abstract boolean comparesImages();
    @Override protected Class<?>[] additionalPrecedingTests() {
        return PrecedingTestUtils.add(super.additionalPrecedingTests(),
                (comparesImages() ? FilesProvidedSupport.configuration().checks().expectedImagePrerequisites()
                    : FilesProvidedSupport.configuration().checks().expectedContentPrerequisites()).apply(provider()));
    }
}
