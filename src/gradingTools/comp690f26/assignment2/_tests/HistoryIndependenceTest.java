package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.common.commands.*;
import java.nio.file.*;
import java.util.*;


import static gradingTools.comp690f26.assignment2._tests.A1A2ScopeRegressionTest.*;

/** Cached failed/partial scores verify prerequisite independence and required
 * translation gates. These are grading-framework tests, not trace mutations. */
public final class HistoryIndependenceTest {
    private static final double PARTIAL=0.5;
    public static void main(String[] args)throws Exception {
        if(args.length!=2)throw new IllegalArgumentException("Supply A2 and A1 correct directories");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        F26Assignment1Suite.setTraceDirectory(args[1]);BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
        promptAndAnswerIndependence();
        var image=gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedImageMatchesGenericImage.class;
        var retention=gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousImagesRetained.class;
        var translation=gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaImageMatchesGenericImage.class;
        var unrelated=List.of(gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousSystemPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousUserPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousCompletedResponsesRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.A1OllamaExpectedContextWindowCompaction.class,
            gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow.class);
        for(double fraction:List.of(0.0,PARTIAL)) {
            JUnitTestsEnvironment.clearCachedJUnitTestCases();
            for(var type:unrelated) ((PassFailJUnitTestCase)type.getDeclaredConstructor().newInstance()).setLastResult(new TestCaseResult(fraction,"Unrelated assessment"));
            full(image);full(retention);
            for(var type:unrelated)require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==fraction,"Image check overwrote unrelated score");
        }
        JUnitTestsEnvironment.getPassFailJUnitTest(translation).setLastResult(new TestCaseResult(false,"Required image translation"));
        reject(image);full(retention);

        System.out.println("HistoryIndependenceTest passed: independent grades, partial credit and required translation vetoes");
        System.exit(0);
    }

    private static void promptAndAnswerIndependence() throws Exception {
        for (String provider : List.of("Gemini", "Ollama")) {
            String root="gradingTools.comp690f26.";
            var prompt=Class.forName(root+"common.expectedRequestContentSuite.Adaptive"+provider+"ExpectedPromptsInProviderRequests");
            var retained=Class.forName(root+"assignment1.contextHistorySuite."+provider+"PreviousCompletedResponsesRetained");
            var text=Class.forName(root+"assignment1.requestTranslationSuite."+provider+"TextualContextWindowMatchesGenericContextWindow");
            var unrelated=List.of(
                Class.forName(root+"assignment1.contextHistorySuite."+provider+"PreviousImagesRetained"),
                Class.forName(root+"assignment1.contextHistorySuite.A1"+provider+"ExpectedContextWindowCompaction"),
                Class.forName(root+"assignment1.responseTranslationSuite."+provider+"ResponseMatchesGenericResponse"));
            for(double fraction:List.of(0.0,PARTIAL)) {
                JUnitTestsEnvironment.clearCachedJUnitTestCases();
                for(var type:unrelated)((PassFailJUnitTestCase)type.getDeclaredConstructor().newInstance()).setLastResult(new TestCaseResult(fraction,"Unrelated assessment"));
                ((PassFailJUnitTestCase)retained.getDeclaredConstructor().newInstance()).setLastResult(new TestCaseResult(fraction,"Independent retention assessment"));
                full(prompt);
                require(JUnitTestsEnvironment.getPassFailJUnitTest(retained).getLastResult().getPercentage()==fraction,"Prompt check overwrote retention grade");
                full(retained);
                for(var type:unrelated)require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==fraction,"Overwrote independent grade");
            }
            JUnitTestsEnvironment.getPassFailJUnitTest(text).setLastResult(new TestCaseResult(false,"Required request translation"));
            reject(prompt);full(retained);
        }
    }
}
