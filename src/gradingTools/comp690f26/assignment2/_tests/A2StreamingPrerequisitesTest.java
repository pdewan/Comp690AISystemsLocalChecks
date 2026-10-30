package gradingTools.comp690f26.assignment2._tests;

import grader.basics.junit.*;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.common.commands.AdaptiveStreamingMatchesSetting;
import gradingTools.comp690f26.common.io.AdaptiveStreamingOutputMatchesSetting;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import java.util.*;

/** Running the setting check on the full session must validate both providers' callbacks.
 * Missing either prerequisite fails this regression; the I/O check must retain
 * only its parsing prerequisite so it remains usable without traces. */
public final class A2StreamingPrerequisitesTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct fixture directory");
        F26Assignment2Suite.setTraceDirectory(args[0]);
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        var method=grader.basics.testcase.PassFailJUnitTestCase.class.getDeclaredMethod("precedingTests");
        method.setAccessible(true);
        var callbacks=List.of(GeminiPartialResponseCallbackInvoked.class,OllamaPartialResponseCallbackInvoked.class);
        // Provider prerequisites are selected from actual requests, after file processing.
        // Check their execution below, rather than requiring unconditional static edges.
        if(!List.of((Class<?>[])method.invoke(new AdaptiveStreamingOutputMatchesSetting())).equals(List.of(CLIOuterOutputSyntax.class)))
            throw new AssertionError("I/O check depends on traces");
        var root=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        var result=JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(AdaptiveStreamingMatchesSetting.class);
        if(!result.getLastResult().isPass()) throw new AssertionError("Setting check failed");
        for(var type:callbacks) if(root.findTest(type).getFractionComplete()!=1) throw new AssertionError("Callback prerequisite did not run: "+type);
        System.out.println("A2StreamingPrerequisitesTest passed: A1 callbacks run before A2 setting check; I/O stays independent");
        System.exit(0);
    }
}
