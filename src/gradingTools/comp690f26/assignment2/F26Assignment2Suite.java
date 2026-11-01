package gradingTools.comp690f26.assignment2;

import grader.basics.junit.BasicJUnitUtils;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.combined.A1A2ExpectedContentSuite;
import gradingTools.comp690f26.combined.A1A2InterfacesSuite;
import gradingTools.comp690f26.combined.A1A2PhysicalStructureSuite;
import gradingTools.comp690f26.combined.A1A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.combined.A1A2TraceIntegritySuite;
import gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite;
import gradingTools.comp690f26.common.io.A2InputOutputConsistencySuite;
import gradingTools.comp690f26.common.translationSuite.TranslationSuite;
import gradingTools.comp690f26.files.A2FilesProvidedSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


@Explanation("From trace evidence to source structure and behavior; failed prerequisites block dependent checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A1A2ExpectedContentSuite.class,
    A1A2SeparationOfConcernsSuite.class,
    A1A2PhysicalStructureSuite.class,
    A1A2InterfacesSuite.class,
    A1A2TraceIntegritySuite.class,
    A2FilesProvidedSuite.class,
    A2InputOutputConsistencySuite.class,
    // Combined groups list atomic tests; the original suites remain separately available.
    TranslationSuite.class,
    StreamingBehaviorSuite.class,
    // A2-only groups remain separate from the reused A1 suites.
    ContextHistorySuite.class,
    A2SemanticsSuite.class,
})
public class F26Assignment2Suite {
    static {
        A2ChecksConfiguration.configureProperties();
    }

    public static void main(String[] args) {
        BasicJUnitUtils.interactiveTest(F26Assignment2Suite.class);
    }
}
