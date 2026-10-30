package gradingTools.comp690f26.assignment2._tests;

import grader.basics.junit.BasicJUnitUtils;
import grader.basics.junit.JUnitTestsEnvironment;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided;


/** Settings prepare only settings; image content still requires attachment preparation. */
public final class A2RequestExpectationsPrerequisiteTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply the correct fixture directory");
        F26Assignment2Suite.setTraceDirectory(args[0]);
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        var root = BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        var comparison = JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(A2CLIRequestSettingsMatch.class);
        if (comparison == null || !comparison.getLastResult().isPass())
            throw new AssertionError("Selecting request comparison first failed");
        if(JUnitTestsEnvironment.getPassFailJUnitTest(A2CLIRequestExpectationsProvided.class)!=null)
            throw new AssertionError("Settings unnecessarily prepared attachments");
        var image=JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(
            gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedImageMatchesGenericImage.class);
        if(!image.getLastResult().isPass()) throw new AssertionError("Image comparison failed");
        var preparation = root.findTest(A2CLIRequestExpectationsProvided.class);
        if (preparation == null || preparation.getFractionComplete() != 1 || preparation.getComputedMaxScore() != 0)
            throw new AssertionError("Zero-point request preparation did not complete as a prerequisite");
        System.out.println("A2RequestExpectationsPrerequisiteTest passed: settings stay independent; image comparison runs content preparation");
        System.exit(0);
    }
}
