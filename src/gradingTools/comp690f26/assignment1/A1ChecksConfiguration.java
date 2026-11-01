package gradingTools.comp690f26.assignment1;
import java.util.function.Function;
import java.util.Optional;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import gradingTools.comp690f26.files.FilesProvidedConfiguration;
import gradingTools.comp690f26.common.TraceEventEvidencePolicy;
import gradingTools.comp690f26.common.TraceChecksConfiguration;

import eduAI.trace.processors.BridgeDemoTraceInformation;
import eduAI.trace.processors.NativeTraceFilesProcessor;
import eduAI.trace.processors.TraceFilesProcessor;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeChunkCallbackAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMessageAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMetadataAdapters;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters;
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
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1GenericRequestStructuresMatchAcrossProviders;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.GeminiServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.OllamaServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import java.nio.file.Path;

/** Assignment 1 file processors, expectations and prerequisite policy. */
public final class A1ChecksConfiguration {
    private A1ChecksConfiguration() { }

    private static Path traceDirectory = FilesProvidedConfiguration.WORKING_DIRECTORY;

    public static void setTraceDirectory(String aTraceDirectory) {
		traceDirectory = FilesProvidedConfiguration.directory(aTraceDirectory);
		configureProperties();
	}
	public static void configureProperties() {
		FilesProvidedSupport.configure(new FilesProvidedConfiguration(Function.identity(),
				A1ChecksConfiguration::createTraceFilesProcessor, Optional.empty(), new TraceChecksConfiguration(
                        java.util.Set.of(), A1ChecksConfiguration::expectedContentPrerequisites, A1ChecksConfiguration::expectedImagePrerequisites, Optional.empty(), Optional.empty(), new eduAI.trace.processors.cli.CLIAttachmentKinds(java.util.Map.of()), TraceEventEvidencePolicy.PRESCRIBED_MODES, java.util.List.of(), java.util.List.of())), traceDirectory);
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
