package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.assignment1.traceIntegritySuite.MessageTranslationsTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.MetadataTranslatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ParametersTranslatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.PartialResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ProviderStreamingChunksReceivedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ResponsesTranslatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandleFactoriesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandlesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceProviderNamesConsistent;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.MessageMergerFactoriesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLICommandDisplayedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLICommandHandledTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLICommandOutputNotifiedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIFileExpandedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIParameterHistoryUpdatedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIPropertyHandledTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIStreamDisplayedTraced;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIStreamOutputNotifiedTraced;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 TraceIntegrity checks with their existing points and prerequisites.")
@MaxValue(44)
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
    CLICommandHandledTraced.class,
    CLIPropertyHandledTraced.class,
    CLIParameterHistoryUpdatedTraced.class,
    CLICommandOutputNotifiedTraced.class,
    CLIStreamOutputNotifiedTraced.class,
    CLICommandDisplayedTraced.class,
    CLIStreamDisplayedTraced.class,
    CLIFileExpandedTraced.class
})
public class A1A2TraceIntegritySuite { }
