package gradingTools.comp690f26.assignment1.commonInterfacesSuite;
import util.annotations.Explanation;

import java.util.List;

import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiPromptMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaPromptMessageAdapterExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, requires one shared inherited interface; gates text/image translation.")
@MaxValue(1)
public class NativePromptMessageAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiPromptMessageAdapterExistsInProject.class,
			OllamaPromptMessageAdapterExistsInProject.class
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
