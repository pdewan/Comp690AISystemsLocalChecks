package gradingTools.comp690f26.assignment1.traceIntegritySuite.extra;

import gradingTools.comp690f26.assignment1.projectClassesSuite.A1TracedClassesExistInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceIntegrityExplanations;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

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
