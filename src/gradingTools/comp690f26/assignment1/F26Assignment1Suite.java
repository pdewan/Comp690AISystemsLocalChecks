package gradingTools.comp690f26.assignment1;
import util.annotations.Explanation;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import grader.basics.config.BasicExecutionSpecificationSelector;
import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite;
import gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.A1PhysicalStructureSuite;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.A1ProviderIndependentTypeIntegritySuite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.A1ProjectClassesSuite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.A1ProjectExtraClassesSuite;
import gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.A1TracedClassesSuite;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.extra.A1TracedExtraClassesSuite;
import gradingTools.comp690f26.assignment1.contextHistorySuite.A1ContextHistorySuite;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1CrossProviderSuite;
import gradingTools.comp690f26.assignment1.expectedRequestContentSuite.A1ExpectedRequestContentSuite;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.A1RequestTranslationSuite;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.A1ResponseTranslationSuite;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.A1StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.A1ExtraStreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.style.A1GeneralStyleSuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.A1TraceIntegritySuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.A1ExtraTraceIntegritySuite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.trace.Tracer;



@Explanation("From trace evidence to source structure and behavior; failed prerequisites block dependent checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	TraceFilesProvided.class,
	A1TraceIntegritySuite.class,
	A1ExtraTraceIntegritySuite.class,
	A1ProviderIndependentTypeIntegritySuite.class,
	A1ResponseTranslationSuite.class,
	A1RequestTranslationSuite.class,
	A1ExpectedRequestContentSuite.class,
	A1ContextHistorySuite.class,
	A1CrossProviderSuite.class,
	A1StreamingBehaviorSuite.class,
	A1ExtraStreamingBehaviorSuite.class,
	A1TracedClassesSuite.class,
	A1TracedExtraClassesSuite.class,
	A1ProjectClassesSuite.class,
	A1ProjectExtraClassesSuite.class,
	A1SeparatedClassesSuite.class,
	A1PhysicalStructureSuite.class,
	A1InterfacesSuite.class,
	A1CommonInterfacesSuite.class,
//	A1GeneralStyleSuite.class
})
public class F26Assignment1Suite {
	static {
		ExplanationLengthTest.assertExplanationsFitTooltips();
	}
	 
	public static void setTraceDirectory(String aTraceDirectory) {
		TraceFilesProvided.setTraceDirectory(aTraceDirectory);
	}
	public static void configureProperties() {
//		BasicProject.setCheckCheckstyleFolder(false);
//		BasicExecutionSpecificationSelector.getBasicExecutionSpecification().
//		setCheckStyleConfiguration("unc_checks_690.xml");
	}

	public static void main (String[] args) {
		try {

//			GraderBasicsTraceUtility.setTracing();
//			BasicProject.setCheckCheckstyleFolder(false);
//			GraderBasicsTraceUtility.setTracerShowInfo(false);

			BasicJUnitUtils.interactiveTest(F26Assignment1Suite.class);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static {
		configureProperties();
	}
}
