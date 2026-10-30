package gradingTools.comp690f26.assignment2._tests;

import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.assignment2.projectClassesSuite.A2TracedClassesExistInProject;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.ExitCommandHandlerIsSeparated;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.CLICommandHandledTraced;

/** Runs checks first through the real grader, including their prerequisite chains. */
public final class A2ProjectGateTest {
    private enum Scenario { CORRECT, MISSING_CLASS, SHARED_EXIT_CLASS, MISSING_EXIT_EVIDENCE }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Supply fixture directory and scenario");
        Scenario scenario = Scenario.valueOf(args[1]);
        F26Assignment2Suite.setTraceDirectory(args[0]);
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
        var event = JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(CLICommandHandledTraced.class);
        var gate = JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(A2TracedClassesExistInProject.class);
        boolean resolvable = scenario != Scenario.MISSING_CLASS;
        require(gate.getLastResult().isPass() == resolvable, "project gate outcome");
        require(event.getLastResult().isPass() == resolvable, "command event must reject the missing participating command class");
        if (resolvable) {
            var exit = JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(ExitCommandHandlerIsSeparated.class);
            require(exit.getLastResult().isPass() == (scenario == Scenario.CORRECT), "exit class separation outcome");
        }
        System.out.println("A2ProjectGateTest passed: " + scenario);
        System.exit(0);
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
