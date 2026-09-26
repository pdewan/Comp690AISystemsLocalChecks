package gradingTools.comp690f26.assignment1.commonInterfacesSuite;
import util.annotations.Explanation;

import java.util.List;

import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiParameterAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaParameterAdapterExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, requires one shared inherited interface; gates parameter comparisons.")
@MaxValue(1)
public class NativeParameterAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiParameterAdapterExistsInProject.class,
			OllamaParameterAdapterExistsInProject.class
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
