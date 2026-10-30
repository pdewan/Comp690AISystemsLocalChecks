package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.MessageMergerFactoriesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation(TraceIntegrityExplanations.SUITE)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    MessageMergerFactoriesFetchedTraced.class,
    StreamingChunksAccumulatedTraced.class,
    StreamingChunksMergedTraced.class,
    CompleteResponseCallbacksTraced.class,
    ServerHandleFactoriesFetchedTraced.class,
    TraceProviderNamesConsistent.class,
    ServerHandlesFetchedTraced.class,
    MessageTranslationsTraced.class,
    ParametersTranslatedTraced.class,
    RequestsTraced.class,
    ResponsesTranslatedTraced.class,
    MetadataTranslatedTraced.class,
    ProviderStreamingChunksReceivedTraced.class,
    PartialResponseCallbacksTraced.class,
})
@MaxValue(28)
public class A1TraceIntegritySuite {
//	static {
//		TraceIntegritySuiteCompositionTest
//				.assertSuiteContainsExactlyRequiredChecks();
//	}
}
