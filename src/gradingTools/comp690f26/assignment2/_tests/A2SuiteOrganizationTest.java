package gradingTools.comp690f26.assignment2._tests;

import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2ObserverObservableSuite;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.assignment2.suites.A2HandlerRegistrationSuite;
import gradingTools.comp690f26.combined.*;
import gradingTools.comp690f26.common.io.A2OutputSyntaxSuite;
import java.util.*;
import org.junit.runners.Suite;

/** Verifies exact atomic ownership, not just passing totals. Class discovery,
 * project lookup and observer structure belong to separation; syntax and
 * delivery belong to semantics. Duplication or nesting in either group fails. */
public final class A2SuiteOrganizationTest {
    private static Set<Class<?>> leaves(Class<?> type) {
        var result=new LinkedHashSet<Class<?>>(); collect(type,result); return result;
    }
    private static void collect(Class<?> type,Set<Class<?>> result) {
        var suite=type.getAnnotation(Suite.SuiteClasses.class);
        if(suite==null) require(result.add(type),"Duplicate check: "+type);
        else for(var child:suite.value()) collect(child,result);
    }
    private static void require(boolean valid,String message) { if(!valid)throw new AssertionError(message); }
    public static void main(String[] args) {
        var all=leaves(F26Assignment2Suite.class);
        var separation=new LinkedHashSet<Class<?>>();
        for(var group:List.of(
            gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite.class,
            A2SeparationOfConcernsSuite.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite.class,
            gradingTools.comp690f26.assignment2.commonInterfacesSuite.A2CommonInterfacesSuite.class,
            A2HandlerRegistrationSuite.class,A1A2ProjectClassesSuite.class,A1A2TracedClassesSuite.class)) separation.addAll(leaves(group));
        var delivery=Set.of(A2CLICommandObserverOutput.class,A2CLIStreamObserverOutput.class);
        var observerStructure=leaves(A2ObserverObservableSuite.class); observerStructure.removeAll(delivery);separation.addAll(observerStructure);
        var semantics=leaves(A2OriginalSemanticsSuite.class);semantics.addAll(leaves(A2OutputSyntaxSuite.class));semantics.addAll(delivery);
        require(separation.equals(leaves(A1A2SeparationOfConcernsSuite.class)),"Wrong separation membership");
        require(semantics.equals(leaves(A2SemanticsSuite.class)),"Wrong semantic membership");
        require(Collections.disjoint(separation,semantics),"Structure and semantics overlap");
        for(var group:List.of(A1A2SeparationOfConcernsSuite.class,A2SemanticsSuite.class))
            for(var child:group.getAnnotation(Suite.SuiteClasses.class).value())
                require(child.getAnnotation(Suite.SuiteClasses.class)==null,"Nested suite: "+child);
        var direct=Set.of(F26Assignment2Suite.class.getAnnotation(Suite.SuiteClasses.class).value());
        require(direct.containsAll(Set.of(A1A2SeparationOfConcernsSuite.class,A2SemanticsSuite.class,A1A2ExpectedContentSuite.class)),"Required UI groups missing");
        for(var removed:List.of(A1A2ProjectClassesSuite.class,A1A2TracedClassesSuite.class,A2ObserverObservableSuite.class,A2OutputSyntaxSuite.class,A2HandlerRegistrationSuite.class))
            require(!direct.contains(removed),"Old heading retained: "+removed);
        require(leaves(gradingTools.comp690f26.files.A2FilesProvidedSuite.class).equals(Set.of(
            gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class,
            gradingTools.comp690f26.files.CLIInputOutputFileProvided.class)),"Files suite must check availability only");
        A2CombinedSuitesTest.verify();
        System.out.println("A2SuiteOrganizationTest passed: "+all.size()+" unique checks; exact structure/semantics ownership, no nested suites");
        System.exit(0);
    }
}
