package gradingTools.comp690f26.assignment1.responseTranslationSuite;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceProviderNames;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.MetadataTranslatedTraced;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import util.annotations.Explanation;


/** Compares metadata values against each metadata event's native source using
 * the registered field/unit mappings. Correct metadata retains credit when
 * response dumps are missing. Missing metadata evidence or incorrect metadata
 * values fail; response integrity remains independently scored. Arbitrary keys pass;
 * using one key for distinct native meanings in one source snapshot fails. Later
 * requests/chunks may update values; equal native values remain ambiguous. */
// Retain this response check's allocation when requests and responses share a suite.
@util.annotations.MaxValue(15)
@Explanation("Checks native metadata values and key collisions within a snapshot; allows student-chosen names.")
public class OllamaMetadataMatchesGenericMetadata
		extends AbstractProviderResponseCheck {
	protected String provider() {
		return TraceProviderNames.OLLAMA;
	}

	protected TraceCheckResult check(TraceFilesProcessor aProcessor) {
		return aProcessor.checkProviderGenericMetadataMatches(provider());
	}

	protected Class<?>[] additionalPrecedingTests() {
		return PrecedingTestUtils.add(super.additionalPrecedingTests(),
			MetadataTranslatedTraced.class);
	}
}
