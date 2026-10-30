package gradingTools.comp690f26.assignment1;

import eduAI.trace.processors.BridgeDemoTraceInformation;
import eduAI.trace.processors.NativeTraceFilesProcessor;
import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.config.BasicExecutionSpecificationSelector;
import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeChunkCallbackAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMessageAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMetadataAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters;
import gradingTools.comp690f26.assignment1.contextHistorySuite.A1ContextCompactionSuite;
import gradingTools.comp690f26.assignment1.contextHistorySuite.A1GeminiExpectedContextWindowCompaction;
import gradingTools.comp690f26.assignment1.contextHistorySuite.A1OllamaExpectedContextWindowCompaction;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousUserPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousCompletedResponsesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousImagesRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousSystemPromptsRetained;
import gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousUserPromptsRetained;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1CrossProviderSuite;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1GenericRequestStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.A1PhysicalStructureSuite;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.GeminiServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.OllamaServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.projectClassesSuite.A1ProjectClassesSuite;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.RequestTranslationSuite;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.ResponseTranslationSuite;
import gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.style.GeneralStyleSuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.A1TraceIntegritySuite;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.A1TracedClassesSuite;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import gradingTools.comp690f26.common.TraceChecksConfiguration;
import gradingTools.comp690f26.common.TraceEventEvidencePolicy;
import gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveExpectedRequestContentSuite;
import gradingTools.comp690f26.common.translationSuite.TranslationSuite;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import gradingTools.comp690f26.files.FilesProvidedConfiguration;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.annotations.Explanation;
import util.trace.Tracer;

@Explanation("From trace evidence to source structure and behavior; failed prerequisites block dependent checks.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptiveTraceFilesProvided.class,
    A1TraceIntegritySuite.class,
    TranslationSuite.class,
    AdaptiveExpectedRequestContentSuite.class,
    ContextHistorySuite.class,
    A1ContextCompactionSuite.class,
    A1CrossProviderSuite.class,
    StreamingBehaviorSuite.class,
    A1TracedClassesSuite.class,
    A1ProjectClassesSuite.class,
    A1SeparatedClassesSuite.class,
    A1PhysicalStructureSuite.class,
    A1InterfacesSuite.class,
    A1CommonInterfacesSuite.class,
    //	GeneralStyleSuite.class
})
public class F26Assignment1Suite {
	private static Path traceDirectory = FilesProvidedConfiguration.WORKING_DIRECTORY;

	static {
		ExplanationLengthTest.assertExplanationsFitTooltips();
	}
	 
	public static void setTraceDirectory(String aTraceDirectory) {
		traceDirectory = FilesProvidedConfiguration.directory(aTraceDirectory);
		configureProperties();
	}
	@BeforeClass
	public static void configureProperties() {
		FilesProvidedSupport.configure(new FilesProvidedConfiguration(Function.identity(),
				F26Assignment1Suite::createTraceFilesProcessor, Optional.empty(), new TraceChecksConfiguration(
                        java.util.Set.of(), F26Assignment1Suite::expectedContentPrerequisites, F26Assignment1Suite::expectedImagePrerequisites, Optional.empty(), Optional.empty(), new eduAI.trace.processors.cli.CLIAttachmentKinds(java.util.Map.of()), TraceEventEvidencePolicy.PRESCRIBED_MODES, java.util.List.of(), java.util.List.of())), traceDirectory);
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

			BasicJUnitUtils.interactiveTest(F26Assignment1Suite.class);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static Class<?>[] contentContinuityPrerequisites(String provider) {
        return switch (provider) {
            case eduAI.trace.processors.TraceProviderNames.GEMINI -> new Class<?>[]{
                GeminiPreviousSystemPromptsRetained.class,
                GeminiPreviousUserPromptsRetained.class,
                GeminiPreviousImagesRetained.class,
                GeminiPreviousCompletedResponsesRetained.class};
            case eduAI.trace.processors.TraceProviderNames.OLLAMA -> new Class<?>[]{
                OllamaPreviousSystemPromptsRetained.class,
                OllamaPreviousUserPromptsRetained.class,
                OllamaPreviousImagesRetained.class,
                OllamaPreviousCompletedResponsesRetained.class};
            default -> throw new IllegalArgumentException("No content prerequisites configured for provider " + provider);
        };
    }
    /** A1's documented expected-content finale includes history, handle structure
     * and cross-provider request agreement, in addition to prescribed text. */
    public static Class<?>[] expectedContentPrerequisites(String provider) {
        return expectedImagePrerequisites(provider);
    }
    /** Restore the committed A1 constraints shared by prompt and image finales. */
    public static Class<?>[] expectedImagePrerequisites(String provider) {
        return PrecedingTestUtils.add(switch(provider) {
            case eduAI.trace.processors.TraceProviderNames.GEMINI -> new Class<?>[]{
                GeminiServerHandleAdapterHasRequiredTypes.class,
                GeminiPreviousSystemPromptsRetained.class,
                GeminiPreviousUserPromptsRetained.class,
                GeminiPreviousImagesRetained.class,
                GeminiPreviousCompletedResponsesRetained.class,
                A1GeminiExpectedContextWindowCompaction.class};
            case eduAI.trace.processors.TraceProviderNames.OLLAMA -> new Class<?>[]{
                OllamaServerHandleAdapterHasRequiredTypes.class,
                OllamaPreviousSystemPromptsRetained.class,
                OllamaPreviousUserPromptsRetained.class,
                OllamaPreviousImagesRetained.class,
                OllamaPreviousCompletedResponsesRetained.class,
                A1OllamaExpectedContextWindowCompaction.class};
            default -> throw new IllegalArgumentException("No image prerequisites configured for provider " + provider);
        }, NativeServerHandleAdapters.class,
            NativePromptMessageAdapters.class,
            NativeParameterAdapters.class,
            NativeResponseMessageAdapters.class,
            NativeResponseMetadataAdapters.class,
            NativeChunkCallbackAdapters.class,
            A1GenericRequestStructuresMatchAcrossProviders.class);
    }

	public static TraceFilesProcessor createTraceFilesProcessor(Path directory) {
		BridgeDemoTraceInformation information = new BridgeDemoTraceInformation(directory);
		var processor = new NativeTraceFilesProcessor(information.traceFiles(), information.expectedInputs());
        processor.setExpectedContentChecks(new eduAI.trace.processors.assignment1.BridgeDemoContentChecks(processor));
        return processor;
	}
}
