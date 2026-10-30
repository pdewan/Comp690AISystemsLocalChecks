package gradingTools.comp690f26.assignment2._tests;

import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment2.interfacesSuite.*;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.ObserverRolesSeparated;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.*;
import java.util.*;

/** Delivery needs event evidence; independent API, storage and role-separation
 * assessments must neither run nor gate delivery or input credit. */
public final class A2ObserverPrerequisitesTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply the correct A2 fixture directory");
        F26Assignment2Suite.setTraceDirectory(args[0]);
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        requirePrerequisite(A2StreamObservableObserverMethods.class, gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamOutputObserverExistsInProject.class);
        requirePrerequisite(A2DispatcherObserverTypes.class, gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandDispatcherExistsInProject.class);
        requirePrerequisite(A2StreamObservableObserverTypes.class, gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamObservableExistsInProject.class);
        requirePrerequisite(A2CLICommandObserverOutput.class, gradingTools.comp690f26.assignment2.traceIntegritySuite.CLICommandDisplayedTraced.class);
        requirePrerequisite(A2CLIStreamObserverOutput.class, gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIStreamDisplayedTraced.class);

        var root = BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        for (var delivery : List.of(A2CLICommandObserverOutput.class, A2CLIStreamObserverOutput.class)) {
            var check = JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(delivery);
            if (!check.getLastResult().isPass()) throw new AssertionError("Delivery failed: " + delivery);
        }
        JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(gradingTools.comp690f26.common.commands.AdaptiveExpectedCommandInputs.class);
        for(var type:List.of(A2DispatcherObserverTypes.class,A2StreamObservableObserverTypes.class,
                A2ObserverNotificationMethods.class,ObserverRolesSeparated.class))
            if(JUnitTestsEnvironment.getPassFailJUnitTest(type)!=null)
                throw new AssertionError("Independent design assessment ran through delivery/input checking: "+type);
        System.out.println("A2ObserverPrerequisitesTest passed: event prerequisites run; API/storage/separation stay independent");
        System.exit(0);
    }
    private static void requirePrerequisite(Class<?> dependent, Class<?> prerequisite) throws Exception {
        var method = grader.basics.testcase.PassFailJUnitTestCase.class.getDeclaredMethod("precedingTests");
        method.setAccessible(true);
        var dependencies = (Class<?>[]) method.invoke(dependent.getDeclaredConstructor().newInstance());
        if (!List.of(dependencies).contains(prerequisite))
            throw new AssertionError(dependent + " must depend on " + prerequisite);
    }
}
