package gradingTools.comp690f26.assignment1;

import grader.basics.junit.BasicJUnitUtils;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite;
import gradingTools.comp690f26.assignment1.contextHistorySuite.A1ContextCompactionSuite;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1CrossProviderSuite;
import gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.A1PhysicalStructureSuite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.A1ProjectClassesSuite;
import gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.A1TraceIntegritySuite;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.A1TracedClassesSuite;
import gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveExpectedRequestContentSuite;
import gradingTools.comp690f26.common.translationSuite.TranslationSuite;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;

@Explanation("From trace evidence to source structure and behavior; failed prerequisites block dependent checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptiveTraceFilesProvided.class,
    A1TraceIntegritySuite.class,
    TranslationSuite.class,
    AdaptiveExpectedRequestContentSuite.class,
    ContextHistorySuite.class,
    A1ContextCompactionSuite.class,
    A1CrossProviderSuite.class,
    StreamingBehaviorSuite.class,
    A1TracedClassesSuite.class,
    A1ProjectClassesSuite.class,
    A1SeparatedClassesSuite.class,
    A1PhysicalStructureSuite.class,
    A1InterfacesSuite.class,
    A1CommonInterfacesSuite.class,
})
public class F26Assignment1Suite {
    static {
        A1ChecksConfiguration.configureProperties();
    }

    public static void main(String[] args) {
        BasicJUnitUtils.interactiveTest(F26Assignment1Suite.class);
    }
}
