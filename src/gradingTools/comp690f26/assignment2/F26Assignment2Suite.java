package gradingTools.comp690f26.assignment2;

import eduAI.trace.processors.CLITraceInformation;
import eduAI.trace.processors.NativeTraceFilesProcessor;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.cli.CLIMixedIOProcessor;
import eduAI.trace.processors.cli.CLIProcessors;
import eduAI.trace.processors.cli.CLIPropertyDescriptor;
import eduAI.trace.processors.cli.CLIPropertyDescriptor.ValueType;
import grader.basics.config.BasicExecutionSpecificationSelector;
import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1GenericRequestStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1GenericResponseStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiMetadataMatchesGenericMetadata;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaMetadataMatchesGenericMetadata;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiMergedStreamingResponseInNextContextWindow;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaMergedStreamingResponseInNextContextWindow;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.A1TraceIntegritySuite;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.parameterHistorySuite.A2CLIParameterHistorySnapshotsPreserved;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLISemanticsContract;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.A2TraceIntegritySuite;
import gradingTools.comp690f26.combined.A1A2ExpectedContentSuite;
import gradingTools.comp690f26.combined.A1A2InterfacesSuite;
import gradingTools.comp690f26.combined.A1A2PhysicalStructureSuite;
import gradingTools.comp690f26.combined.A1A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.combined.A1A2TraceIntegritySuite;
import gradingTools.comp690f26.common.CLICommandChecksConfiguration;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import gradingTools.comp690f26.common.TraceChecksConfiguration;
import gradingTools.comp690f26.common.TraceEventEvidencePolicy;
import gradingTools.comp690f26.common.commands.AdaptiveStreamingMatchesSetting;
import gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedImageMatchesGenericImage;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedPromptsInProviderRequests;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedImageMatchesGenericImage;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedPromptsInProviderRequests;
import gradingTools.comp690f26.common.io.A2InputOutputConsistencySuite;
import gradingTools.comp690f26.common.io.AdaptiveMessagesOutputMatches;
import gradingTools.comp690f26.common.io.AdaptivePropertyOutputMatches;
import gradingTools.comp690f26.common.io.AdaptiveStreamingOutputMatchesSetting;
import gradingTools.comp690f26.common.io.AdaptiveSystemMessagesOutputMatches;
import gradingTools.comp690f26.common.io.AdaptiveTranscriptOutputMatches;
import gradingTools.comp690f26.common.io.CLIMessagesOutputSyntax;
import gradingTools.comp690f26.common.translationSuite.TranslationSuite;
import gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided;
import gradingTools.comp690f26.files.A2FilesProvidedSuite;
import gradingTools.comp690f26.files.FilesProvidedConfiguration;
import gradingTools.comp690f26.files.FilesProvidedConfiguration.InteractionsSource;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import java.util.ArrayList;
import java.util.List;
import util.annotations.Explanation;
import util.trace.Tracer;


@Explanation("From trace evidence to source structure and behavior; failed prerequisites block dependent checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A1A2ExpectedContentSuite.class,
    A1A2SeparationOfConcernsSuite.class,
    A1A2PhysicalStructureSuite.class,
    A1A2InterfacesSuite.class,
    A1A2TraceIntegritySuite.class,
    A2FilesProvidedSuite.class,
    A2InputOutputConsistencySuite.class,
    // Combined groups list atomic tests; the original suites remain separately available.
    TranslationSuite.class,
    StreamingBehaviorSuite.class,
    // A2-only groups remain separate from the reused A1 suites.
    ContextHistorySuite.class,
    A2SemanticsSuite.class,
})
public class F26Assignment2Suite {
    /** Required advertised families; model version changes do not change these checks. */
    public static final String REQUIRED_IMAGE_ATTACHMENT="Image.png", REQUIRED_TEXT_ATTACHMENT="Commentary.txt";
    private static final List<String> INITIAL_PROPERTIES = List.of(eduAI.properties.AIPropertyNames.CURRENT_MODELS, eduAI.properties.AIPropertyNames.SYSTEM_PROMPT,
            eduAI.properties.AIPropertyNames.TEMPERATURE, eduAI.properties.AIPropertyNames.TIMEOUT,
            eduAI.properties.AIPropertyNames.NUM_CTX, eduAI.properties.AIPropertyNames.TOP_K, eduAI.properties.AIPropertyNames.STREAM);
    private static final List<String> CHANGED_PROPERTIES = List.of(eduAI.properties.AIPropertyNames.CURRENT_MODELS,eduAI.properties.AIPropertyNames.TEMPERATURE,eduAI.properties.AIPropertyNames.TIMEOUT);
    public static final List<String> REQUIRED_MODEL_PREFIXES = List.of("gemini-", "llava");
	private static Path traceDirectory = FilesProvidedConfiguration.WORKING_DIRECTORY;

		 
	public static void setTraceDirectory(String selected) {
		traceDirectory = FilesProvidedConfiguration.directory(selected);
		configureProperties();
	}

	@BeforeClass
	public static void configureProperties() {
		FilesProvidedSupport.configure(new FilesProvidedConfiguration(
				directory -> new CLITraceInformation(directory).directory(),
				F26Assignment2Suite::createTraceFilesProcessor,
				Optional.of(new InteractionsSource(CLITraceInformation.INTERACTIONS_FILE_NAME,
						F26Assignment2Suite::createInteractionsProcessor)),
                new TraceChecksConfiguration(Set.of(
                    A1GenericRequestStructuresMatchAcrossProviders.class,
                    A1GenericResponseStructuresMatchAcrossProviders.class
                    ),
                    F26Assignment2Suite::expectedContentPrerequisites, F26Assignment2Suite::expectedImagePrerequisites, Optional.of(propertyExpectations()),
                    Optional.of(new CLICommandChecksConfiguration(
                        F26Assignment2Suite::createCommandResultCheckers,commandExpectations())), attachmentKinds(), TraceEventEvidencePolicy.OBSERVED_REQUEST_MODES,
                    java.util.List.of(A2CLIRequestSettingsMatch.class, A2CLIParameterHistorySnapshotsPreserved.class,
                        AdaptiveStreamingMatchesSetting.class,
                        AdaptiveStreamingOutputMatchesSetting.class,
                        AdaptivePropertyOutputMatches.class),
                    java.util.List.of(
                        // The command finale requires valid message grammar and
                        // trace semantics. Individual view checks remain independent.
                        CLIMessagesOutputSyntax.class,
                        A2CLISemanticsContract.class,
                        AdaptiveGeminiExpectedPromptsInProviderRequests.class,
                        AdaptiveOllamaExpectedPromptsInProviderRequests.class,
                        AdaptiveGeminiExpectedImageMatchesGenericImage.class,
                        AdaptiveOllamaExpectedImageMatchesGenericImage.class,
                        AdaptiveTranscriptOutputMatches.class,
                        AdaptiveMessagesOutputMatches.class,
                        AdaptiveSystemMessagesOutputMatches.class,
                        A2CLITextFileExpansionContentsMatch.class, A2CLIImageFileExpansionContentsMatch.class,
                        A2CLICommandObserverOutput.class, A2CLIStreamObserverOutput.class))), traceDirectory);
		
//		BasicProject.setCheckCheckstyleFolder(false);
//		BasicExecutionSpecificationSelector.getBasicExecutionSpecification().
//		setCheckStyleConfiguration("unc_checks_690.xml");
	}

	public static void main (String[] args) {
		configureProperties();
		try {

//			GraderBasicsTraceUtility.setTracing();
//			BasicProject.setCheckCheckstyleFolder(false);
//			GraderBasicsTraceUtility.setTracerShowInfo(false);

			BasicJUnitUtils.interactiveTest(F26Assignment2Suite.class);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /** Request-content comparisons include earlier streaming answers. Validate
     * their retention for this provider before accepting the reconstructed history.
     * Expected content also requires native-to-generic metadata translation, like parameters.
     * Optional completion callbacks are not needed to retain the merged answer. */
    public static Class<?>[] expectedContentPrerequisites(String provider) {
        Class<?> retainedStreamingAnswer = switch(provider) {
            case eduAI.trace.processors.TraceProviderNames.GEMINI ->
                GeminiMergedStreamingResponseInNextContextWindow.class;
            case eduAI.trace.processors.TraceProviderNames.OLLAMA ->
                OllamaMergedStreamingResponseInNextContextWindow.class;
            default -> throw new IllegalArgumentException("No streaming-history prerequisites configured for provider " + provider);
        };
        return PrecedingTestUtils.add(
                F26Assignment1Suite.contentContinuityPrerequisites(provider),
                A2CLIRequestExpectationsProvided.class,
                retainedStreamingAnswer, switch(provider) {
                    case eduAI.trace.processors.TraceProviderNames.GEMINI -> GeminiMetadataMatchesGenericMetadata.class;
                    case eduAI.trace.processors.TraceProviderNames.OLLAMA -> OllamaMetadataMatchesGenericMetadata.class;
                    default -> throw new IllegalArgumentException("No metadata prerequisites configured for provider " + provider);
                });
    }
    /** Image comparisons consume request images, not retained text or answers.
     * Provider image translation and request integrity are added by the shared check. */
    public static Class<?>[] expectedImagePrerequisites(String provider) {
        return switch(provider) {
            case eduAI.trace.processors.TraceProviderNames.GEMINI, eduAI.trace.processors.TraceProviderNames.OLLAMA ->
                new Class<?>[]{A2CLIRequestExpectationsProvided.class};
            default -> throw new IllegalArgumentException("No image prerequisites configured for provider " + provider);
        };
    }

	public static TraceFilesProcessor createTraceFilesProcessor(Path directory) {
		return new NativeTraceFilesProcessor(new CLITraceInformation(directory).traceFile());
	}

	/** Grading grammar is independent of student command/property implementations. */
	public static CLIMixedIOProcessor createInteractionsProcessor() {
		return CLIProcessors.mixedIO(CLIProcessors.input(propertyDescriptors(),
				Set.of(eduAI.trace.processors.cli.CLICommandNames.NEW, eduAI.trace.processors.cli.CLICommandNames.MESSAGES,
						eduAI.trace.processors.cli.CLICommandNames.TRANSCRIPT, "bye", "help"), Set.of()));
	}
    public static eduAI.trace.processors.cli.CLIAttachmentKinds attachmentKinds() {
        return new eduAI.trace.processors.cli.CLIAttachmentKinds(Map.of(
            REQUIRED_IMAGE_ATTACHMENT,eduAI.trace.processors.cli.CLIFileExpansionContentsChecks.Kind.IMAGE,
            REQUIRED_TEXT_ATTACHMENT,eduAI.trace.processors.cli.CLIFileExpansionContentsChecks.Kind.TEXT));
    }

	public static Map<String, CLIPropertyDescriptor> propertyDescriptors() {
		return Map.of(
				eduAI.properties.AIPropertyNames.CURRENT_MODELS, property(ValueType.STRING_LIST),
				eduAI.properties.AIPropertyNames.SYSTEM_PROMPT, property(ValueType.STRING),
				eduAI.properties.AIPropertyNames.TEMPERATURE, property(ValueType.DOUBLE),
				eduAI.properties.AIPropertyNames.TIMEOUT, property(ValueType.INTEGER),
				eduAI.properties.AIPropertyNames.STREAM, property(ValueType.BOOLEAN),
				eduAI.properties.AIPropertyNames.STREAMING_ALIAS, property(ValueType.BOOLEAN),
				eduAI.properties.AIPropertyNames.NUM_CTX, property(ValueType.INTEGER),
				eduAI.properties.AIPropertyNames.TOP_K, property(ValueType.INTEGER));
	}
    public static eduAI.trace.processors.cli.CLIPropertyExpectations propertyExpectations() {
        var steps = new java.util.ArrayList<eduAI.trace.processors.cli.CLIPropertyExpectations.Step>();
        var initial = INITIAL_PROPERTIES;
        for(var name:initial) steps.add(propertyStep(name,true,false,
            eduAI.properties.AIPropertyNames.STREAM.equals(name)?Optional.of(Boolean.FALSE.toString()):Optional.empty()));
        for(var name:initial) steps.add(propertyStep(name,false,false,Optional.empty()));
        var changed=CHANGED_PROPERTIES;
        for(var name:changed) steps.add(propertyStep(name,true,true,Optional.empty()));
        for(var name:changed) steps.add(propertyStep(name,false,false,Optional.empty()));
        steps.add(propertyStep(eduAI.properties.AIPropertyNames.STREAM,true,true,Optional.of(Boolean.TRUE.toString())));
        steps.add(propertyStep(eduAI.properties.AIPropertyNames.CURRENT_MODELS,true,true,Optional.empty()));
        steps.add(propertyStep(eduAI.properties.AIPropertyNames.STREAM,true,true,Optional.of(Boolean.FALSE.toString())));
        return new eduAI.trace.processors.cli.CLIPropertyExpectations(propertyDescriptors(),steps,requiredModelChoices());
    }
    public static eduAI.trace.processors.cli.CLICommandExpectations commandExpectations() {
        var steps=new java.util.ArrayList<eduAI.trace.processors.cli.CLICommandExpectations.Step>();
        for(var ignored:INITIAL_PROPERTIES) steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        for(var ignored:INITIAL_PROPERTIES) steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty(),REQUIRED_IMAGE_ATTACHMENT));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty()));
        addHistorySteps(steps);
        for(var ignored:CHANGED_PROPERTIES) steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        for(var ignored:CHANGED_PROPERTIES) steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.MESSAGES,Optional.of("")));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty(),REQUIRED_TEXT_ATTACHMENT));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROPERTY,Optional.empty()));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.PROMPT,Optional.empty()));
        addHistorySteps(steps);
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.EXIT,Optional.of("")));
        return new eduAI.trace.processors.cli.CLICommandExpectations(steps);
    }
    private static void addHistorySteps(java.util.List<eduAI.trace.processors.cli.CLICommandExpectations.Step> steps) {
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.TRANSCRIPT,Optional.of("")));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.MESSAGES,Optional.of("")));
        steps.add(commandStep(eduAI.trace.processors.cli.CLICommandNames.MESSAGES,Optional.of(eduAI.trace.processors.cli.CLICommandNames.SYSTEM_OPTION)));
    }
    private static eduAI.trace.processors.cli.CLICommandExpectations.Step commandStep(String command,Optional<String> argument,String... attachments) {
        return new eduAI.trace.processors.cli.CLICommandExpectations.Step(command,argument,List.of(attachments));
    }
    public static List<eduAI.trace.processors.cli.CLIModelChoiceRequirement> requiredModelChoices() {
        return REQUIRED_MODEL_PREFIXES.stream().map(prefix -> new eduAI.trace.processors.cli.CLIModelChoiceRequirement(
            eduAI.trace.processors.cli.CLIModelChoiceRequirement.Match.PREFIX,prefix)).toList();
    }
    private static eduAI.trace.processors.cli.CLIPropertyExpectations.Step propertyStep(String name,boolean setter,
            boolean changed,Optional<String> value) {
        return new eduAI.trace.processors.cli.CLIPropertyExpectations.Step(name,setter
            ? eduAI.trace.processors.cli.CLIInteraction.Kind.PROPERTY_SET : eduAI.trace.processors.cli.CLIInteraction.Kind.PROPERTY_GET,value,changed);
    }

	/** One registration point for independently graded command-result rules. */
	public static eduAI.trace.processors.cli.CLICommandResultCheckerRegistry createCommandResultCheckers() {
		var registry = new eduAI.trace.processors.cli.CLICommandResultCheckerRegistry();
		registry.register(eduAI.trace.processors.cli.CLICommandNames.PROPERTY, new eduAI.trace.processors.cli.CLIPropertyCommandResultChecker());
		registry.register(eduAI.trace.processors.cli.CLICommandNames.PROMPT, new eduAI.trace.processors.cli.CLIPromptCommandResultChecker());
		registry.register(eduAI.trace.processors.cli.CLICommandNames.TRANSCRIPT, new eduAI.trace.processors.cli.CLITranscriptCommandResultChecker());
		registry.register(eduAI.trace.processors.cli.CLICommandNames.MESSAGES, new eduAI.trace.processors.cli.CLIMessagesCommandResultChecker());
		return registry;
	}

	/** One registration point for property grading; checker implementations can be shared. */
	public static eduAI.trace.processors.cli.CLIPropertyCheckerRegistry createPropertyCheckers() {
		var registry = eduAI.trace.processors.cli.CLIPropertyCheckerRegistry.fromDescriptors(propertyDescriptors());
		registry.registerAlias(eduAI.properties.AIPropertyNames.STREAMING_ALIAS, eduAI.properties.AIPropertyNames.STREAM);
		return registry;
	}

	private static CLIPropertyDescriptor property(ValueType type) {
		return new CLIPropertyDescriptor(type,
				type == ValueType.BOOLEAN ? List.of("true", "false") : List.of());
	}
}
