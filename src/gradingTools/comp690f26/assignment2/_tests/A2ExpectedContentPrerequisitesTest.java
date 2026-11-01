package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import gradingTools.comp690f26.common.properties.*;
import gradingTools.comp690f26.common.io.*;
import gradingTools.comp690f26.common.commands.*;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLISemanticsContract;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.assignment2.suites.A2ExpectedContentSuite;
import gradingTools.comp690f26.assignment2.suites.A2HandlerRegistrationSuite;
import gradingTools.comp690f26.common.io.A2InputOutputConsistencySuite;
import gradingTools.comp690f26.common.io.A2OutputSyntaxSuite;
import org.junit.runners.Suite;


/** Starts from an empty grading cache and selects ONLY A2 expected content.
 * All consumed semantic/syntax/translation evidence must receive visible grades.
 * Unrelated architectural and optional checks must remain unrun. Missing visible
 * grades fail even if the finale passes. Failed required grades must block their
 * consumer; independent failed/partial grades must not block or lose credit.
 * The whole assignment suite is constructed for UI metadata, never executed. */
public final class A2ExpectedContentPrerequisitesTest {
    private static final double PARTIAL_CREDIT = 0.5;
    private static final List<String> failures = new ArrayList<>();
    private static final Set<Class<?>> required = new LinkedHashSet<>();
    private static final Set<Class<?>> independent = new LinkedHashSet<>();
    private static void leaves(Class<?> type, Set<Class<?>> destination) {
        var suite = type.getAnnotation(Suite.SuiteClasses.class);
        if (suite == null) destination.add(type);
        else for (var child : suite.value()) leaves(child, destination);
    }
    private static void require(boolean condition, String message) { if (!condition) failures.add(message); }
    private static TestCaseResult fresh(Class<?> type) {
        var runner = new org.junit.runner.JUnitCore(); var listener = new AJUnitTestResult();
        runner.addListener(listener); runner.run(type);
        var result = listener.getTestCaseResult();
        JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);
        return result;
    }
    private static void requiredFailureBlocks(Class<?> prerequisite, Class<?> dependent) {
        var prior = JUnitTestsEnvironment.getPassFailJUnitTest(prerequisite);
        var consumer = JUnitTestsEnvironment.getPassFailJUnitTest(dependent);
        // Do not mask a missing visible grade with a control-setup exception.
        if (prior == null || prior.getLastResult() == null || consumer == null || consumer.getLastResult() == null) return;
        var saved = prior.getLastResult();
        var consumerSaved = consumer.getLastResult();
        prior.setLastResult(new TestCaseResult(false, "Required prerequisite failure control"));
        require(fresh(dependent).isFail(), "Failed prerequisite ignored: " + dependent.getSimpleName() + " -> " + prerequisite.getSimpleName());
        prior.setLastResult(saved);
        JUnitTestsEnvironment.getPassFailJUnitTest(dependent).setLastResult(consumerSaved);
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply correct A2 trace directory");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        A2ChecksConfiguration.setTraceDirectory(args[0]);
        var root = BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        double totalBefore = root.getComputedMaxScore(), regularBefore = root.getComputedRegularMaxScore();
        for (var suite : List.of(A2ExpectedContentSuite.class, A2SemanticsSuite.class,
                A2OutputSyntaxSuite.class, A2InputOutputConsistencySuite.class, A2TraceIntegritySuite.class,
                gradingTools.comp690f26.assignment1.requestTranslationSuite.RequestTranslationSuite.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite.class,
                gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite.class,
                gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveExpectedRequestContentSuite.class)) leaves(suite, required);
        // Metadata translation is required by the expected-content finale, like parameters.
        leaves(A1TraceIntegritySuite.class, required);
        // Group membership does not create a dependency on optional factory or
        // completion events; expected content still uses only its own chain.
        required.remove(gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.MessageMergerFactoriesFetchedTraced.class);
        required.remove(gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced.class);
        leaves(gradingTools.comp690f26.assignment1.responseTranslationSuite.ResponseTranslationSuite.class, required);
        required.addAll(List.of(A2CLICommandObserverOutput.class,A2CLIStreamObserverOutput.class,
            gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse.class,
            gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse.class,
            gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced.class,
            gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced.class));
        for (var suite : List.of(A2HandlerRegistrationSuite.class,A2SeparationOfConcernsSuite.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2PhysicalStructureSuite.class,
                gradingTools.comp690f26.assignment2.interfacesSuite.A2InterfacesSuite.class,
                gradingTools.comp690f26.assignment2.commonInterfacesSuite.A2CommonInterfacesSuite.class,
                gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite.class,
                gradingTools.comp690f26.assignment1.physicalStructureSuite.A1PhysicalStructureSuite.class,
                gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite.class,
                gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.ExtraStreamingBehaviorSuite.class,
                gradingTools.comp690f26.assignment1.projectClassesSuite.extra.A1ProjectExtraClassesSuite.class)) leaves(suite, independent);
        independent.addAll(List.of(
            gradingTools.comp690f26.assignment2.interfacesSuite.A2ObserverNotificationMethods.class,
            gradingTools.comp690f26.assignment2.interfacesSuite.A2DispatcherObserverMethods.class,
            gradingTools.comp690f26.assignment2.interfacesSuite.A2StreamObservableObserverMethods.class,
            gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced.class));
        // This structural check is a genuine semantic prerequisite: retained
        // store contents must belong to the same store across observations.
        independent.remove(PropertyStoreIdentityConsistent.class);
        required.add(PropertyStoreIdentityConsistent.class);
        // Optional completion leaves now share the streaming UI group, but are
        // still not prerequisites of expected content.
        required.removeIf(type -> type.isAnnotationPresent(util.annotations.IsExtra.class)
            && type.getAnnotation(util.annotations.IsExtra.class).value());
        require(Collections.disjoint(required, independent), "Regression policy classifies a check both ways");
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
        root.findTest(gradingTools.comp690f26.combined.A1A2ExpectedContentSuite.class).test();
        for (var type : required) {
            var test = JUnitTestsEnvironment.getPassFailJUnitTest(type);
            require(test != null && test.getLastResult() != null && test.getLastResult().getPercentage() == 1,
                "Missing visible passing prerequisite: " + type.getSimpleName());
        }
        for (var type : independent) {
            var test = JUnitTestsEnvironment.getPassFailJUnitTest(type);
            require(test == null || test.getLastResult() == null, "Independent check executed: " + type.getSimpleName());
        }
        // A required failure is distinct from an unrelated failure. Preserve the
        // saved positive results between these controls to avoid cache pollution.
        requiredFailureBlocks(PropertyStoreConsistent.class, AdaptiveExpectedPropertyInputs.class);
        requiredFailureBlocks(A2CLIRequestSettingsMatch.class, AdaptiveExpectedPropertyInputs.class);
        requiredFailureBlocks(CLIPropertyHandledTraced.class, A2CLISemanticsContract.class);
        requiredFailureBlocks(CLIMessagesOutputSyntax.class, AdaptiveExpectedCommandInputs.class);
        requiredFailureBlocks(A2CLISemanticsContract.class, AdaptiveExpectedCommandInputs.class);
        requiredFailureBlocks(A2CLIStreamObserverOutput.class, AdaptiveExpectedCommandInputs.class);
        requiredFailureBlocks(RequestsTraced.class, gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow.class);
        requiredFailureBlocks(gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiMetadataMatchesGenericMetadata.class,
            gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedPromptsInProviderRequests.class);
        requiredFailureBlocks(gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaMetadataMatchesGenericMetadata.class,
            gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedPromptsInProviderRequests.class);
        var seeded = new LinkedHashMap<PassFailJUnitTestCase, Double>();
        for (var type : List.of(CommandHandlersRegistered.class, PropertyHandlerInstancesSeparated.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2DispatcherObserverTypes.class,
                gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse.class)) {
            var test = type.getDeclaredConstructor().newInstance();
            double fraction = type == PropertyHandlerInstancesSeparated.class ? PARTIAL_CREDIT : 0;
            test.setLastResult(new TestCaseResult(fraction, "Independent failed/partial grade control"));
            seeded.put(test,fraction);
        }
        for (var type : List.of(AdaptiveExpectedPropertyInputs.class,AdaptiveExpectedCommandInputs.class))
            require(fresh(type).getPercentage() == 1, "Independent failure blocked/scaled expected content: " + type.getSimpleName());
        for (var entry : seeded.entrySet()) require(entry.getKey().getLastResult().getPercentage() == entry.getValue(),
            "Independent grade overwritten: " + entry.getKey().getClass().getSimpleName());
        require(root.getComputedMaxScore() == totalBefore && root.getComputedRegularMaxScore() == regularBefore, "Grading weights changed");
        failures.forEach(s -> System.out.println("REGRESSION: " + s));
        System.out.println("A2ExpectedContentPrerequisitesTest: " + failures.size() + " failures; " + required.size()
            + " required and " + independent.size() + " independent checks audited");
        System.exit(failures.isEmpty() ? 0 : 1);
    }
}
