package gradingTools.comp690f26.assignment2._tests;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;
import java.util.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import org.junit.runners.Suite;
import util.annotations.IsExtra;
import util.annotations.MaxValue;


/**
 * Reused released suites must retain their own points and extra-credit flags.
 * Moving a scored suite under an unscored grouping fails this regression even
 * if every leaf test remains present and passes. No score is assigned here.
 */
public final class A2ScorePreservationTest {
    private static final double EXPECTED_HISTORY_CHECK_POINTS = 10;
    private record Score(double total, boolean extra) { }
    public static void main(String[] args) {
        A1ChecksConfiguration.configureProperties();
        var a1 = BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class).rootNode;
        Map<Class<?>, Score> released = new HashMap<>();
        for (var child : a1.children()) released.put(child.getJUnitClass(), new Score(child.getComputedMaxScore(), child.isExtra()));
        A2ChecksConfiguration.configureProperties();
        var a2 = BasicJUnitUtils.toGradableTree(A2OriginalLayout.class).rootNode;
        verifyHistoryWeights(a1, true);
        verifyHistoryWeights(a2, false);
        verifyRequestTranslationWeights(a1);
        verifyRequestTranslationWeights(a2);
        verifyTypeIntegrityWeights(a1);
        verifyTypeIntegrityWeights(a2);
        verifyStreamingWeights(a1);
        verifyStreamingWeights(a2);
        verifyMergedOptionalGroups(a1);
        verifyMergedOptionalGroups(a2);
        verifySeparationWeights(a1);
        verifySeparationWeights(a2);
        for (var child : a2.children()) {
            Score expected = released.get(child.getJUnitClass());
            if (expected != null && (!expected.equals(new Score(child.getComputedMaxScore(), child.isExtra()))))
                throw new AssertionError("Released suite scoring changed: " + child.getJUnitClass());
        }
        var direct = List.of(A2OriginalLayout.class.getAnnotation(Suite.SuiteClasses.class).value());
        for (var type : direct) requireNoScoredDescendant(type);
        System.out.println("A2ScorePreservationTest passed: " + a2.getComputedRegularMaxScore()
            + " regular, " + (a2.getComputedMaxScore()-a2.getComputedRegularMaxScore()) + " extra; reused A1 scores unchanged");
        A2CombinedSuitesTest.verify();
        System.exit(0);
    }
    /** Type-integrity leaves must retain explicit zero weights even when the
     * containing translation suite distributes points to unweighted requests. */
    private static void verifyTypeIntegrityWeights(GradableJUnitSuite root) {
        var original=gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.ProviderIndependentTypeIntegritySuite.class;
        for(var type:original.getAnnotation(Suite.SuiteClasses.class).value()) {
            var annotation=type.getDeclaredAnnotation(MaxValue.class);
            if(annotation==null || annotation.value()!=0 || root.findTest(type).getComputedMaxScore()!=0)
                throw new AssertionError("Type-integrity check must remain explicitly zero points: "+type);
        }
    }
    private static void verifyMergedOptionalGroups(GradableJUnitSuite root) {
        for(var group:root.children())
            if(group.isExtra() || group.getJUnitClass().getSimpleName().contains("Extra"))
                throw new AssertionError("Separate optional UI group remains: "+group.getJUnitClass());
        for(var type:gradingTools.comp690f26.assignment1.projectClassesSuite.extra.A1ProjectExtraClassesSuite.class
                .getAnnotation(Suite.SuiteClasses.class).value()) {
            var leaf=root.findTest(type);
            if(!leaf.isExtra() || leaf.getComputedMaxScore()!=2)
                throw new AssertionError("Optional project role must keep two extra points: "+type);
        }
        for(var type:gradingTools.comp690f26.assignment1.tracedClassesSuite.extra.A1TracedExtraClassesSuite.class
                .getAnnotation(Suite.SuiteClasses.class).value()) {
            var leaf=root.findTest(type);
            if(!leaf.isExtra() || leaf.getComputedMaxScore()!=0)
                throw new AssertionError("Optional traced role must keep its zero-point extra flag: "+type);
        }
        for(var type:gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.A1ExtraTraceIntegritySuite.class
                .getAnnotation(Suite.SuiteClasses.class).value()) {
            var leaf=root.findTest(type);
            if(leaf.isExtra() || leaf.getComputedMaxScore()!=2)
                throw new AssertionError("Moving integrity checks must preserve their existing regular points: "+type);
        }
    }
    /** Completion remains extra credit even when regular and optional checks
     * share a UI group. A lost flag, changed weight or duplicate leaf fails. */
    private static void verifyStreamingWeights(GradableJUnitSuite root) {
        var suite=(GradableJUnitSuite)root.findTest(gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite.class);
        if(suite.children().size()!=8 || suite.getComputedRegularMaxScore()!=90 || suite.getComputedMaxScore()!=120)
            throw new AssertionError("Streaming must contain eight checks: 90 regular + 30 extra points");
        var optional=Set.of(
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse.class,
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.OllamaCompleteResponseCallbackInvokedWithMergedResponse.class);
        for(var leaf:suite.children()) {
            boolean extra=optional.contains(leaf.getJUnitClass());
            if(leaf.isExtra()!=extra || (extra && leaf.getComputedMaxScore()!=15))
                throw new AssertionError("Wrong streaming extra-credit grade: "+leaf.getJUnitClass());
        }
    }
    /** An individual override or unequal allocation must fail, even if the
     * suite total still happens to be 120. This expectation is rubric data. */
    private static void verifyRequestTranslationWeights(GradableJUnitSuite root) {
        var suiteType=gradingTools.comp690f26.assignment1.requestTranslationSuite.RequestTranslationSuite.class;
        double requestPoints=java.util.Arrays.stream(suiteType.getAnnotation(Suite.SuiteClasses.class).value())
            .mapToDouble(type->root.findTest(type).getComputedMaxScore()).sum();
        if(requestPoints!=120) throw new AssertionError("Request translation checks must total 120 points");
        for(var check:suiteType.getAnnotation(Suite.SuiteClasses.class).value()) {
            if(check.getDeclaredAnnotation(MaxValue.class)!=null || root.findTest(check).getComputedMaxScore()!=20)
                throw new AssertionError("Request translation must inherit 20 points from the suite: "+check);
        }
    }
    private static void verifySeparationWeights(GradableJUnitSuite root) {
        var extras = Set.of(
            gradingTools.comp690f26.assignment1.separatedClassesSuite.ServerHandleFactoryRegistryIsSeparated.class,
            gradingTools.comp690f26.assignment1.separatedClassesSuite.GenericContextWindowMessageIsSeparated.class,
            gradingTools.comp690f26.assignment1.separatedClassesSuite.ParameterStoreIsSeparated.class);
        var suite = (GradableJUnitSuite) root.findTest(
            gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite.class);
        if (suite.getComputedRegularMaxScore()!=70 || suite.getComputedMaxScore()!=85)
            throw new AssertionError("Separation must retain 70 regular plus 15 extra points");
        for (var check : suite.children()) {
            if (check.getComputedMaxScore()!=5 || check.isExtra()!=extras.contains(check.getJUnitClass()))
                throw new AssertionError("Wrong separation check weight or extra-credit flag: "+check.getJUnitClass());
        }
    }
    private static void verifyHistoryWeights(GradableJUnitSuite root, boolean includesCompaction) {
        var shared = gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite.class;
        var compact = gradingTools.comp690f26.assignment1.contextHistorySuite.A1ContextCompactionSuite.class;
        Class<?> suiteType = root.getJUnitClass();
        var direct = List.of(suiteType.getAnnotation(Suite.SuiteClasses.class).value());
        if (!direct.contains(shared) || direct.contains(compact) != includesCompaction)
            throw new AssertionError("Wrong retention/compaction suite membership: " + root.getJUnitClass());
        Set<Class<?>> history = new HashSet<>(List.of(shared.getAnnotation(Suite.SuiteClasses.class).value()));
        if (includesCompaction) history.addAll(List.of(compact.getAnnotation(Suite.SuiteClasses.class).value()));
        List<GradableJUnitTest> leaves = new ArrayList<>();
        root.fillLeafNodeDescendents(leaves);
        for (var leaf : leaves) if (history.contains(leaf.getJUnitClass())) {
            if (leaf.getComputedMaxScore() != EXPECTED_HISTORY_CHECK_POINTS)
                throw new AssertionError("History check weight changed: " + leaf.getJUnitClass() + " " + leaf.getComputedMaxScore());
            history.remove(leaf.getJUnitClass());
        }
        if (!history.isEmpty()) throw new AssertionError("History checks missing: " + history);
    }
    private static void requireNoScoredDescendant(Class<?> parent) {
        var suite = parent.getAnnotation(Suite.SuiteClasses.class);
        if (suite == null) return;
        for (var child : suite.value()) {
            if (child.getAnnotation(Suite.SuiteClasses.class) == null) continue;
            var points = child.getAnnotation(MaxValue.class);
            if ((points != null && points.value() != 0) || child.isAnnotationPresent(IsExtra.class))
                throw new AssertionError("Nested suite loses points/extra-credit status in the grader: " + child);
            requireNoScoredDescendant(child);
        }
    }
}
