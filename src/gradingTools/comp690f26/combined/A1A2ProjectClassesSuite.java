package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.assignment1.projectClassesSuite.A1TracedClassesExistInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiChunkCallbackAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiParameterAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiPromptMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiResponseMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiResponseMetadataAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleFactoryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GenericContextWindowMessageExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaChunkCallbackAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaParameterAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaPromptMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaResponseMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaResponseMetadataAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaServerHandleFactoryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.ParameterStoreExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.ServerHandleFactoryRegistryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryRegistryExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.A2TracedClassesExistInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandDispatcherExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandOutputObserverExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIImageFileExpanderExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIPropertyHandlerRegistryExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIPropertyHandlersExistInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamObservableExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamOutputObserverExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLITextFileExpanderExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CommandHandlerRegistryExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.ExitCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.MessagesCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.PromptCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.PropertyCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.TranscriptCommandHandlerExistsInProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 ProjectClasses checks with their existing points and prerequisites.")
@MaxValue(68)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    StreamChunkMergerExistsInProject.class,
    StreamChunkMergerFactoryExistsInProject.class,
    StreamChunkMergerFactoryRegistryExistsInProject.class,
    A1TracedClassesExistInProject.class,
    ServerHandleFactoryRegistryExistsInProject.class,
    GenericContextWindowMessageExistsInProject.class,
    ParameterStoreExistsInProject.class,
    GeminiServerHandleAdapterExistsInProject.class,
    OllamaServerHandleAdapterExistsInProject.class,
    GeminiServerHandleFactoryExistsInProject.class,
    OllamaServerHandleFactoryExistsInProject.class,
    GeminiPromptMessageAdapterExistsInProject.class,
    OllamaPromptMessageAdapterExistsInProject.class,
    GeminiResponseMessageAdapterExistsInProject.class,
    OllamaResponseMessageAdapterExistsInProject.class,
    GeminiParameterAdapterExistsInProject.class,
    OllamaParameterAdapterExistsInProject.class,
    GeminiResponseMetadataAdapterExistsInProject.class,
    OllamaResponseMetadataAdapterExistsInProject.class,
    GeminiChunkCallbackAdapterExistsInProject.class,
    OllamaChunkCallbackAdapterExistsInProject.class,
    A2TracedClassesExistInProject.class,
    CLICommandDispatcherExistsInProject.class,
    CommandHandlerRegistryExistsInProject.class,
    PromptCommandHandlerExistsInProject.class,
    PropertyCommandHandlerExistsInProject.class,
    TranscriptCommandHandlerExistsInProject.class,
    MessagesCommandHandlerExistsInProject.class,
    CLIPropertyHandlersExistInProject.class,
    ExitCommandHandlerExistsInProject.class,
    CLICommandOutputObserverExistsInProject.class,
    CLIStreamOutputObserverExistsInProject.class,
    CLIStreamObservableExistsInProject.class,
    CLITextFileExpanderExistsInProject.class,
    CLIImageFileExpanderExistsInProject.class,
    CLIPropertyHandlerRegistryExistsInProject.class
})
public class A1A2ProjectClassesSuite { }
