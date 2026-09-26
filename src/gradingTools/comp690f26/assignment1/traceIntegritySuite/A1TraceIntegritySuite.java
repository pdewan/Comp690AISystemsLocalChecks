package gradingTools.comp690f26.assignment1.traceIntegritySuite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AllTracedClassesExistInProject;

@Explanation(TraceIntegrityExplanations.SUITE)
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AllTracedClassesExistInProject.class,
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
@MaxValue(20)
public class A1TraceIntegritySuite {
//	static {
//		TraceIntegritySuiteCompositionTest
//				.assertSuiteContainsExactlyRequiredChecks();
//	}
}
