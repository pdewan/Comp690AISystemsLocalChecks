package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryRegistryExistsInProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Resolves trace-derived names to project classes used by reflection and the integrity gate.")
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
})
@MaxValue(40)
public class A1ProjectClassesSuite {
	 
	
}
