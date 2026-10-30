package gradingTools.comp690f26.assignment1.requestTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.GeminiContextWindowTypeIntegrity;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Compares the two request dumps, not response translation. Matching request
 * text passes despite a bad final response or incorrect parameter values; missing context or changed text fails.
 * Checks that use responses as their oracle declare that dependency themselves. */
@Explanation("After context-type checks, compares request text independently of response translation.")
public class GeminiTextualContextWindowMatchesGenericContextWindow extends AbstractProviderRequestCheck {
	protected String provider() {
		return TraceProviderNames.GEMINI;
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
				GeminiContextWindowTypeIntegrity.class);
	}

	protected TraceCheckResult check(TraceFilesProcessor p) {
		return p.checkProviderGenericTextualContextWindowMatches(provider());
	}
}
