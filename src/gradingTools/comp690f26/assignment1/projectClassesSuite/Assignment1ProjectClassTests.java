package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.extra.StreamChunkMergerFactoryRegistryExistsInProject;

public final class Assignment1ProjectClassTests {
	private static final Class[] ALL = {
		GenericContextWindowMessageExistsInProject.class,
		ParameterStoreExistsInProject.class,
		GeminiServerHandleAdapterExistsInProject.class,
		OllamaServerHandleAdapterExistsInProject.class,
		GeminiServerHandleFactoryExistsInProject.class,
		OllamaServerHandleFactoryExistsInProject.class,
		ServerHandleFactoryRegistryExistsInProject.class,
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
		StreamChunkMergerExistsInProject.class,
		StreamChunkMergerFactoryExistsInProject.class,
		StreamChunkMergerFactoryRegistryExistsInProject.class
	};

	private Assignment1ProjectClassTests() {
	}

	public static Class[] all() {
		return ALL.clone();
	}
}
