package gradingTools.comp690f26.assignment2._tests;

import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.util.*;

/** The original layout is an independent scoring baseline. Combining suites
 * must preserve every leaf's points and effective extra-credit flag, except
 * the authorized A2 allocation of two points per required A1 project check.
 * A changed unrelated weight, duplicate, or lost extra-credit flag fails. */
public final class A2CombinedSuitesTest {
    private record Score(double points, boolean extra) { }
    private static void collect(GradableJUnitTest node, boolean inheritedExtra, Map<Class<?>,Score> scores) {
        boolean extra=inheritedExtra || node.isExtra();
        if(node instanceof GradableJUnitSuite suite) {
            for(var child:suite.children()) collect(child,extra,scores);
        } else if(scores.put(node.getJUnitClass(),new Score(node.getComputedMaxScore(),extra))!=null)
            throw new AssertionError("Duplicate check: "+node.getJUnitClass());
    }
    public static void main(String[] args) {
        verify();
        System.exit(0);
    }
    public static void verify() {
        F26Assignment2Suite.configureProperties();
        var original=BasicJUnitUtils.toGradableTree(A2OriginalLayout.class).rootNode;
        var combined=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        var before=new HashMap<Class<?>,Score>(); var after=new HashMap<Class<?>,Score>();
        collect(original,false,before); collect(combined,false,after);
        var projectChecks=gradingTools.comp690f26.assignment1.projectClassesSuite.A1ProjectClassesSuite.class
            .getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value();
        for(var check:projectChecks) {
            var score=before.get(check);
            if(!score.extra()) before.put(check,new Score(2,false));
        }
        if(!before.equals(after)) {
            for(var entry:before.entrySet()) if(!entry.getValue().equals(after.get(entry.getKey())))
                System.out.println("Changed: "+entry.getKey().getSimpleName()+" "+entry.getValue()+" -> "+after.get(entry.getKey()));
            throw new AssertionError("Combined layout changed membership, scores or extra credit");
        }
        if(after.size()!=217 || combined.getComputedRegularMaxScore()!=1018 || combined.getComputedMaxScore()!=1069)
            throw new AssertionError("Assignment rubric totals changed");
        long groups=combined.children().stream().filter(n->n.getJUnitClass().getPackageName().equals("gradingTools.comp690f26.combined")).count();
        if(groups!=5) throw new AssertionError("Expected five combined groups");
        var separation=gradingTools.comp690f26.combined.A1A2SeparationOfConcernsSuite.class;
        var separationGrade=combined.findTest(separation);
        if(separationGrade.getComputedRegularMaxScore()!=222 || separationGrade.getComputedMaxScore()!=243)
            throw new AssertionError("Separation must total exactly 222 regular plus 21 extra points");
        var separationAtoms=Set.of(separation.getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value());
        for(var common:List.of(gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite.class,
                gradingTools.comp690f26.assignment2.commonInterfacesSuite.A2CommonInterfacesSuite.class,
                gradingTools.comp690f26.assignment2.suites.A2HandlerRegistrationSuite.class))
            if(!separationAtoms.containsAll(List.of(common.getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value())))
                throw new AssertionError("Common-interface check outside separation group: "+common);
        var translation=combined.findTest(gradingTools.comp690f26.common.translationSuite.TranslationSuite.class);
        if(!(translation instanceof GradableJUnitSuite suite) || suite.children().size()!=16 || suite.getComputedMaxScore()!=180)
            throw new AssertionError("Unified translation must contain sixteen atomic checks worth 180 points");
        for(var group:combined.children()) if(group.getJUnitClass().getPackageName().equals("gradingTools.comp690f26.combined"))
            for(var child:((GradableJUnitSuite)group).children()) if(child instanceof GradableJUnitSuite || child.getJUnitClass().isAnnotationPresent(org.junit.runners.Suite.SuiteClasses.class))
                throw new AssertionError("Combined suite contains a nested suite: "+group.getJUnitClass());
        System.out.println("A2CombinedSuitesTest passed: 5 groups, 217 unique checks; project checks two points each, other weights unchanged; 1018 regular + 51 extra");
    }
}
