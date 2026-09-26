package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AllTracedClassesExistInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.MessageMergerFactoriesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;
@Explanation("Validates merger and final-callback evidence before dependent streaming checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	MessageMergerFactoriesFetchedTraced.class,
	StreamingChunksAccumulatedTraced.class,
	StreamingChunksMergedTraced.class,
	CompleteResponseCallbacksTraced.class,
	
})
@MaxValue(8)
public class A1ExtraTraceIntegritySuite {
//	static {
//		TraceIntegritySuiteCompositionTest
//				.assertSuiteContainsExactlyRequiredChecks();
//	}
}
