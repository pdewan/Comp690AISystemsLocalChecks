package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import gradingTools.comp690f26.assignment2.projectClassesSuite.*;
import gradingTools.comp690f26.assignment2.interfacesSuite.*;
import gradingTools.comp690f26.assignment2.commonInterfacesSuite.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment2.F26Assignment2Suite;

/** Each general interface check must visibly execute the project aggregate.
 * A failed aggregate blocks both general rules, but not the role-specific
 * common-interface rules. Partial unrelated grades must remain untouched. */
public final class A2InterfaceAggregatePrerequisitesTest {
    private static final double PARTIAL_CREDIT = 0.5;
    private static TestCaseResult fresh(Class<?> type) {
        var core=new org.junit.runner.JUnitCore(); var listener=new AJUnitTestResult();
        core.addListener(listener); core.run(type);
        var result=listener.getTestCaseResult();
        JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);
        return result;
    }
    private static void require(boolean condition,String message) {
        if(!condition)throw new AssertionError(message);
    }
    public static void main(String[] args) {
        if(args.length!=1)throw new IllegalArgumentException("Supply correct A2 directory");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        A2ChecksConfiguration.setTraceDirectory(args[0]);
        for(var general:List.of(A2AllTaggedClassesImplementSingleInterface.class,A2TaggedFieldsUseInterfaces.class)) {
            var root=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
            JUnitTestsEnvironment.clearCachedJUnitTestCases();
            require(root.findTest(general).test().getPercentage()==1,"Correct interface check failed: "+general);
            var aggregate=JUnitTestsEnvironment.getPassFailJUnitTest(A2TracedClassesExistInProject.class);
            require(aggregate!=null && aggregate.getLastResult()!=null && aggregate.getLastResult().getPercentage()==1,
                "Aggregate not visibly run by "+general);
            var saved=aggregate.getLastResult();
            aggregate.setLastResult(new TestCaseResult(false,"Required aggregate failure control"));
            require(fresh(general).isFail(),"Failed aggregate did not block "+general);
            for(var common:List.of(A2CommandHandlersCommonInterface.class,A2PropertyHandlersCommonInterface.class,A2PropertyHandlerResultTypes.class))
                require(fresh(common).getPercentage()==1,"Aggregate wrongly blocks common interface: "+common);
            require(aggregate.getLastResult().isFail(),"Common interfaces reran failed aggregate");
            aggregate.setLastResult(saved);
            var unrelated=JUnitTestsEnvironment.getPassFailJUnitTest(A2CommandHandlersCommonInterface.class);
            unrelated.setLastResult(new TestCaseResult(PARTIAL_CREDIT,"Independent common-interface grade"));
            require(fresh(general).getPercentage()==1,"Unrelated common-interface grade reduced general credit");
            require(unrelated.getLastResult().getPercentage()==PARTIAL_CREDIT,"Unrelated grade overwritten");
        }
        A2CombinedSuitesTest.verify();
        System.out.println("A2InterfaceAggregatePrerequisitesTest passed: both visible chains, required failure vetoes, common-interface independence and unchanged weights");
        System.exit(0);
    }
}
