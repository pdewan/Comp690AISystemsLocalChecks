package gradingTools.comp690f26.assignment2._tests;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;
import gradingTools.comp690f26.assignment1.separatedClassesSuite.*;
import gradingTools.comp690f26.assignment1.projectClassesSuite.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.util.List;


/** Executes the separation entry point in both assignments. Checks its own
 * project evidence is triggered, unrelated failed/partial aggregate grades stay
 * independent, and failure of its required project role blocks credit. Cached
 * prerequisite results are controls, not synthetic trace mutations. */
public final class A1SeparationPrerequisitesTest {
    private static final double PARTIAL = 0.5;
    private static final double SEPARATION_POINTS = 85;
    private record Pair(Class<?> separation, Class<?> project) { }
    private static final List<Pair> CHECKS = List.of(
        new Pair(ServerHandleFactoryRegistryIsSeparated.class,ServerHandleFactoryRegistryExistsInProject.class),
        new Pair(GenericContextWindowMessageIsSeparated.class,GenericContextWindowMessageExistsInProject.class),
        new Pair(ParameterStoreIsSeparated.class,ParameterStoreExistsInProject.class));
    public static void main(String[] args) throws Exception {
        if (args.length!=2) throw new IllegalArgumentException("Supply correct A1 and A2 directories");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        for (boolean cli : List.of(false,true)) {
            JUnitTestsEnvironment.clearCachedJUnitTestCases();
            if(cli) { A2ChecksConfiguration.configureProperties(); A2ChecksConfiguration.setTraceDirectory(args[1]); }
            else { A1ChecksConfiguration.configureProperties(); A1ChecksConfiguration.setTraceDirectory(args[0]); }
            var root=BasicJUnitUtils.toGradableTree(cli?F26Assignment2Suite.class:F26Assignment1Suite.class).rootNode;
            double separationPoints=java.util.Arrays.stream(A1SeparatedClassesSuite.class.getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value())
                .mapToDouble(type->root.findTest(type).getComputedMaxScore()).sum();
            if(separationPoints!=SEPARATION_POINTS)
                throw new AssertionError("Separation total changed");
            var execution=new org.junit.runner.JUnitCore().run(A1SeparatedClassesSuite.class);
            if(!execution.wasSuccessful()) throw new AssertionError(execution.getFailures());
            var unrunAggregate=JUnitTestsEnvironment.getPassFailJUnitTest(A1TracedClassesExistInProject.class);
            if(unrunAggregate!=null && unrunAggregate.getLastResult()!=null)
                throw new AssertionError("Separation unexpectedly executed the aggregate");
            // Establish an aggregate instance only for the cached fail/partial controls.
            // Its execution here is test setup, not a production prerequisite.
            if(!run(A1TracedClassesExistInProject.class).isPass()) throw new AssertionError("Correct project classes failed");
            for(var pair:CHECKS) {
                var project=JUnitTestsEnvironment.getPassFailJUnitTest(pair.project);
                if(project.getLastResult()==null || !project.getLastResult().isPass())
                    throw new AssertionError("Separation did not trigger project lookup: "+pair.project);
                var aggregate=JUnitTestsEnvironment.getPassFailJUnitTest(A1TracedClassesExistInProject.class);
                for(double fraction:List.of(0.0,PARTIAL)) {
                    aggregate.setLastResult(new TestCaseResult(fraction,"Independent aggregate control"));
                    if(run(pair.separation).getPercentage()!=1) throw new AssertionError("Unrelated aggregate blocks "+pair.separation);
                    if(aggregate.getLastResult().getPercentage()!=fraction) throw new AssertionError("Aggregate rerun as a side effect");
                }
                var saved=project.getLastResult();
                project.setLastResult(new TestCaseResult(0,"Required project class unavailable"));
                if(!run(pair.separation).isFail()) throw new AssertionError("Missing own project evidence accepted");
                project.setLastResult(saved);
            }
        }
        System.out.println("A1SeparationPrerequisitesTest passed: A1/A2 separation entry points, project lookups, independent grades and required vetoes");
        System.exit(0);
    }
    private static TestCaseResult run(Class<?> type) {
        var core=new org.junit.runner.JUnitCore(); var listener=new AJUnitTestResult();
        core.addListener(listener); core.run(type); return listener.getTestCaseResult();
    }
}
