package gradingTools.comp690f26.assignment1.commonInterfacesSuite;
import util.annotations.Explanation;

import java.util.List;

import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiChunkCallbackAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaChunkCallbackAdapterExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, requires one shared inherited interface; gates partial-callback checks.")
@MaxValue(1)
public class NativeChunkCallbackAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiChunkCallbackAdapterExistsInProject.class,
			OllamaChunkCallbackAdapterExistsInProject.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected List<Class<?>> getClasses() {
		return getClassesFromPrecedingProjectTests();
	}
}
