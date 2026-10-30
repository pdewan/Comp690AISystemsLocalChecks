package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiChunkCallbackAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiParameterAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiPromptMessageAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiResponseMessageAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiResponseMetadataAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiServerHandleAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiServerHandleFactoryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GenericContextWindowMessageFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaChunkCallbackAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaParameterAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaPromptMessageAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaResponseMessageAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaResponseMetadataAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaServerHandleAdapterFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaServerHandleFactoryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.ParameterStoreFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.ServerHandleFactoryRegistryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFactoryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFactoryRegistryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLICommandDispatcherFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLICommandOutputObserverFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIImageFileExpanderFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIPropertyHandlerRegistryFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIPropertyHandlersFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIStreamObservableFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLIStreamOutputObserverFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CLITextFileExpanderFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.CommandHandlerRegistryFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.ExitCommandHandlerFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.MessagesCommandHandlerFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.PromptCommandHandlerFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.PropertyCommandHandlerFoundInTrace;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.TranscriptCommandHandlerFoundInTrace;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 TracedClasses checks with their existing points and prerequisites.")
@MaxValue(0)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    StreamChunkMergerFoundInTrace.class,
    StreamChunkMergerFactoryFoundInTrace.class,
    StreamChunkMergerFactoryRegistryFoundInTrace.class,
    GenericContextWindowMessageFoundInTrace.class,
    ParameterStoreFoundInTrace.class,
    GeminiServerHandleAdapterFoundInTrace.class,
    OllamaServerHandleAdapterFoundInTrace.class,
    GeminiServerHandleFactoryFoundInTrace.class,
    OllamaServerHandleFactoryFoundInTrace.class,
    GeminiPromptMessageAdapterFoundInTrace.class,
    OllamaPromptMessageAdapterFoundInTrace.class,
    GeminiResponseMessageAdapterFoundInTrace.class,
    OllamaResponseMessageAdapterFoundInTrace.class,
    GeminiParameterAdapterFoundInTrace.class,
    OllamaParameterAdapterFoundInTrace.class,
    GeminiResponseMetadataAdapterFoundInTrace.class,
    OllamaResponseMetadataAdapterFoundInTrace.class,
    GeminiChunkCallbackAdapterFoundInTrace.class,
    OllamaChunkCallbackAdapterFoundInTrace.class,
    ServerHandleFactoryRegistryFoundInTrace.class,
    CLICommandDispatcherFoundInTrace.class,
    CommandHandlerRegistryFoundInTrace.class,
    PromptCommandHandlerFoundInTrace.class,
    PropertyCommandHandlerFoundInTrace.class,
    TranscriptCommandHandlerFoundInTrace.class,
    MessagesCommandHandlerFoundInTrace.class,
    CLIPropertyHandlersFoundInTrace.class,
    ExitCommandHandlerFoundInTrace.class,
    CLICommandOutputObserverFoundInTrace.class,
    CLIStreamOutputObserverFoundInTrace.class,
    CLIStreamObservableFoundInTrace.class,
    CLITextFileExpanderFoundInTrace.class,
    CLIImageFileExpanderFoundInTrace.class,
    CLIPropertyHandlerRegistryFoundInTrace.class
})
public class A1A2TracedClassesSuite { }
