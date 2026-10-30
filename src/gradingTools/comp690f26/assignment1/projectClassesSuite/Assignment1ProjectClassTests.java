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

	public static boolean isOptional(Class<?> type) {
        return type == StreamChunkMergerExistsInProject.class
            || type == StreamChunkMergerFactoryExistsInProject.class
            || type == StreamChunkMergerFactoryRegistryExistsInProject.class;
    }

    /** Required roles veto failures; the aggregate rejects unresolved optional roles only when traced. */
    public static Class<?>[] interfacePrerequisites() {
        return gradingTools.comp690f26.common.PrecedingTestUtils.add(all(), A1TracedClassesExistInProject.class);
    }

    public static Class[] all() {
		return ALL.clone();
	}
}
