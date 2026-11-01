package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment2.observerSuite.A2ObserverObservableSuite;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLISemanticsContract;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.assignment2.suites.A2ExpectedContentSuite;
import gradingTools.comp690f26.assignment2.suites.A2HandlerRegistrationSuite;
import gradingTools.comp690f26.common.io.A2InputOutputConsistencySuite;
import gradingTools.comp690f26.common.io.A2OutputSyntaxSuite;

/** Independent rubric expectations catch lost nested weights and unintended zero-point checks. */
public final class A2WeightParityTest {
    private static final Map<String, Double> EXPECTED_SUITE_POINTS = Map.ofEntries(
        Map.entry("A2OutputSyntaxSuite", 20.0), Map.entry("A2InputOutputConsistencySuite", 55.0),
        Map.entry("A2InterfacesSuite", 15.0), Map.entry("A2FilesProvidedSuite", 0.0),
        Map.entry("A2SeparationOfConcernsSuite", 60.0), Map.entry("A2TraceIntegritySuite", 16.0),
        Map.entry("A2ExpectedContentSuite", 60.0), Map.entry("A2PhysicalStructureSuite", 8.0),
        Map.entry("A2HandlerRegistrationSuite", 8.0), Map.entry("A2ProjectClassesSuite", 28.0),
        Map.entry("A2CommonInterfacesSuite", 3.0), Map.entry("A2SemanticsSuite", 145.0),
        Map.entry("A2ObserverObservableSuite", 31.0), Map.entry("A2TracedClassesSuite", 0.0));
    private static final Map<String, Double> UNIFORM_ROLE_POINTS = Map.of(
        "A2OutputSyntaxSuite", 5.0, "A2ProjectClassesSuite", 2.0, "A2TraceIntegritySuite", 2.0,
        "A2PhysicalStructureSuite", 2.0, "A2CommonInterfacesSuite", 1.0,
        "A2SeparationOfConcernsSuite", 5.0, "A2HandlerRegistrationSuite", 2.0,
        "A2ExpectedContentSuite", 30.0);
    private static final Set<String> UNSCORED_GATES = Set.of(
        "A2TracedClassesExistInProject", "A2CLISemanticsContract",
        "AdaptiveTraceFilesProvided", "A2CLIRequestExpectationsProvided", "CLIInputOutputFileProvided");
    public static void main(String[] args) {
        A2ChecksConfiguration.configureProperties();
        var root = BasicJUnitUtils.toGradableTree(A2OriginalLayout.class).rootNode;
        Set<String> remaining = new HashSet<>(EXPECTED_SUITE_POINTS.keySet());
        for (var suite : root.children()) {
            String name = suite.getJUnitClass()==A2OriginalSemanticsSuite.class ? "A2SemanticsSuite" : suite.getJUnitClass().getSimpleName();
            if (!remaining.remove(name)) continue;
            require(suite.getComputedMaxScore() == EXPECTED_SUITE_POINTS.get(name), "Suite weight: " + name);
            var leaves = new ArrayList<GradableJUnitTest>();
            ((GradableJUnitSuite) suite).fillLeafNodeDescendents(leaves);
            for (var leaf : leaves) {
                String check = leaf.getJUnitClass().getSimpleName();
                if (UNSCORED_GATES.contains(check) || name.equals("A2TracedClassesSuite"))
                    require(leaf.getComputedMaxScore() == 0, "Gate received points: " + check);
                else {
                    require(leaf.getComputedMaxScore() > 0, "Unscored assessment: " + check);
                    if (UNIFORM_ROLE_POINTS.containsKey(name))
                        require(leaf.getComputedMaxScore() == UNIFORM_ROLE_POINTS.get(name), "Role weight: " + check);
                }
            }
        }
        require(remaining.isEmpty(), "Missing suites: " + remaining);
        for(var type:gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite.class
                .getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value())
            require(type.getDeclaredAnnotation(util.annotations.MaxValue.class)!=null && type.getDeclaredAnnotation(util.annotations.MaxValue.class).value()==5,
                "Separation check must retain its five-point allocation: "+type);
        var ioWeights=Map.of(
            gradingTools.comp690f26.common.io.AdaptiveStreamingOutputMatchesSetting.class,15,
            gradingTools.comp690f26.common.io.AdaptivePropertyOutputMatches.class,10,
            gradingTools.comp690f26.common.io.AdaptiveTranscriptOutputMatches.class,15,
            gradingTools.comp690f26.common.io.AdaptiveMessagesOutputMatches.class,10,
            gradingTools.comp690f26.common.io.AdaptiveSystemMessagesOutputMatches.class,5);
        ioWeights.forEach((type,points)->require(root.findTest(type).getComputedMaxScore()==points,"I/O consistency weight: "+type));
        require(root.findTest(gradingTools.comp690f26.common.commands.AdaptiveOrdinaryMessagesCommandResultsMatch.class).getComputedMaxScore()==10,"Ordinary messages weight");
        require(root.findTest(gradingTools.comp690f26.common.commands.AdaptiveSystemMessagesCommandResultsMatch.class).getComputedMaxScore()==5,"System messages weight");
        for(var check:List.of(gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch.class,
                gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch.class))
            require(root.findTest(check).getComputedMaxScore()==10, "Attachment kind weight: "+check);
        require(root.getComputedRegularMaxScore() == 1016, "Regular total");
        require(root.getComputedMaxScore() == 1067, "Total with extra credit");
        System.out.println("A2WeightParityTest passed: effective per-role weights, gates, suite totals and extra credit");
        A2CombinedSuitesTest.verify();
        System.exit(0);
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
