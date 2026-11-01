package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import gradingTools.comp690f26.common.expectedRequestContentSuite.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.*;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.*;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.*;
import gradingTools.comp690f26.assignment1.crossProviderSuite.*;
import gradingTools.comp690f26.assignment1.contextHistorySuite.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;

import gradingTools.comp690f26.assignment1.F26Assignment1Suite;


/** Reproduces the documented entry points, not a full-suite run. Expected
 * content must visibly grade history, handle structure and request comparison;
 * streaming must visibly grade its integrity prerequisites. An internal true
 * result with no recorded JUnit grade fails this regression. Failed required
 * history must block the finale, without erasing previously earned credit.
 * Optional completion callbacks remain independent. */
public final class VisibleA1PrerequisitesTest {
    private static final List<Class<?>> CONTENT_REQUIREMENTS = List.of(
        GeminiServerHandleAdapterHasRequiredTypes.class, OllamaServerHandleAdapterHasRequiredTypes.class,
        GeminiPreviousSystemPromptsRetained.class, OllamaPreviousSystemPromptsRetained.class,
        GeminiPreviousUserPromptsRetained.class, OllamaPreviousUserPromptsRetained.class,
        GeminiPreviousImagesRetained.class, OllamaPreviousImagesRetained.class,
        GeminiPreviousCompletedResponsesRetained.class, OllamaPreviousCompletedResponsesRetained.class,
        A1GeminiExpectedContextWindowCompaction.class, A1OllamaExpectedContextWindowCompaction.class,
        A1GenericRequestStructuresMatchAcrossProviders.class, RequestsTraced.class,
        TraceProviderNamesConsistent.class,
        gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters.class,
        gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters.class,
        gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters.class,
        gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMessageAdapters.class);
    private static final List<Class<?>> STREAM_REQUIREMENTS = List.of(
        ProviderStreamingChunksReceivedTraced.class, PartialResponseCallbacksTraced.class,
        StreamingChunksAccumulatedTraced.class, StreamingChunksMergedTraced.class);
    private static final List<String> failures = new ArrayList<>();
    private static GradableJUnitSuite root;
    private static void visible(Class<?> type) {
        var test = JUnitTestsEnvironment.getPassFailJUnitTest(type);
        if (test == null || test.getLastResult() == null || test.getLastResult().getPercentage() != 1)
            failures.add("No visible passing result: " + type.getSimpleName());
    }
    private static TestCaseResult run(Class<?> type) {
        var entry = Objects.requireNonNull(root.findTest(type), "Missing grading entry: " + type);
        if (type.isAnnotationPresent(org.junit.runners.Suite.SuiteClasses.class)) return entry.test();
        // Force a fresh leaf execution for injected prerequisite failures. A GUI
        // entry can reuse its cached success, which would invalidate this control.
        var runner = new org.junit.runner.JUnitCore();
        var listener = new AJUnitTestResult(); runner.addListener(listener); runner.run(type);
        var result = listener.getTestCaseResult();
        JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);
        return result;
    }
    public static void main(String[] args) {
        if (args.length != 1) throw new IllegalArgumentException("Supply correct A1 trace directory");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        A1ChecksConfiguration.configureProperties(); A1ChecksConfiguration.setTraceDirectory(args[0]);
        root = BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class).rootNode;
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
        for (String provider : List.of(eduAI.trace.processors.TraceProviderNames.GEMINI, eduAI.trace.processors.TraceProviderNames.OLLAMA))
            if (!Arrays.asList(A1ChecksConfiguration.expectedImagePrerequisites(provider)).contains(A1GenericRequestStructuresMatchAcrossProviders.class))
                failures.add("Expected image finale lacks cross-provider request constraint: " + provider);
        run(AdaptiveExpectedRequestContentSuite.class);
        CONTENT_REQUIREMENTS.forEach(VisibleA1PrerequisitesTest::visible);
        run(A1CrossProviderSuite.class);
        run(gradingTools.comp690f26.common.translationSuite.TranslationSuite.class);
        var optionalBeforeStreaming = JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse.class);
        if(optionalBeforeStreaming != null && optionalBeforeStreaming.getLastResult() != null)
            failures.add("Expected content forced optional completion");
        run(StreamingBehaviorSuite.class);
        STREAM_REQUIREMENTS.forEach(VisibleA1PrerequisitesTest::visible);
        // Check the entire regular integrity contract after the documented
        // content/cross-provider/response/streaming sequence, not a sample.
        TraceIntegritySuiteCompositionTest.assertSuiteContainsExactlyRequiredChecks();
        for (var type : A1TraceIntegritySuite.class.getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value())
            visible(type);
        // Explicitly selecting the combined streaming suite now runs completion
        // checks too; they retain their individual extra-credit flags.
        run(gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite.class);
        run(gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite.class);
        for (var type : root.getLeafClasses()) visible(type);
        var failedOptional = JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse.class);
        var optionalSaved = failedOptional.getLastResult();
        failedOptional.setLastResult(new TestCaseResult(false, "Independent extra-credit failure control"));
        var regularWithFailedExtra = run(AdaptiveGeminiExpectedPromptsInProviderRequests.class);
        if (!regularWithFailedExtra.isPass())
            failures.add("Extra-credit failure blocked regular expected content: " + regularWithFailedExtra.getNotes());
        if (!failedOptional.getLastResult().isFail()) failures.add("Independent extra-credit grade was overwritten");
        failedOptional.setLastResult(optionalSaved);
        var history = JUnitTestsEnvironment.getPassFailJUnitTest(GeminiPreviousUserPromptsRetained.class);
        if (history != null && history.getLastResult() != null) {
            history.setLastResult(new TestCaseResult(false, "Required history failure control"));
            if (!run(AdaptiveGeminiExpectedPromptsInProviderRequests.class).isFail())
                failures.add("Expected content ignored a failed required history check");
            visible(OllamaPreviousUserPromptsRetained.class);
        }
        requiredFailureBlocks(ServerHandleFactoriesFetchedTraced.class, ServerHandlesFetchedTraced.class);
        requiredFailureBlocks(ServerHandleFactoriesFetchedTraced.class, TraceProviderNamesConsistent.class);
        requiredFailureBlocks(MessageTranslationsTraced.class, RequestsTraced.class);
        requiredFailureBlocks(ParametersTranslatedTraced.class, RequestsTraced.class);
        failures.forEach(s -> System.out.println("REGRESSION: " + s));
        System.out.println("VisibleA1PrerequisitesTest: " + failures.size() + " failures");
        System.exit(failures.isEmpty() ? 0 : 1);
    }
    /** A displayed success is insufficient: each restored edge must veto its
     * dependent on failure, without changing another check's earned score. */
    private static void requiredFailureBlocks(Class<?> prerequisite, Class<?> dependent) {
        var prior = JUnitTestsEnvironment.getPassFailJUnitTest(prerequisite);
        if (prior == null || prior.getLastResult() == null) return; // Already reported by visible().
        var dependentTest = JUnitTestsEnvironment.getPassFailJUnitTest(dependent);
        var saved = prior.getLastResult();
        var dependentSaved = dependentTest == null ? null : dependentTest.getLastResult();
        prior.setLastResult(new TestCaseResult(false, "Required integrity failure control"));
        if (!run(dependent).isFail()) failures.add("Missing failure gate: " + dependent.getSimpleName() + " -> " + prerequisite.getSimpleName());
        prior.setLastResult(saved);
        var current = JUnitTestsEnvironment.getPassFailJUnitTest(dependent);
        if (current != null) current.setLastResult(dependentSaved);
    }
}
