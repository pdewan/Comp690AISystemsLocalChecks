package gradingTools.comp690f26.assignment1.commonInterfacesSuite;
import util.annotations.Explanation;

import java.util.List;

import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaServerHandleAdapterExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, requires one shared inherited interface; gates request/response checks.")
@MaxValue(1)
public class NativeServerHandleAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			TraceFilesProvided.class,
			GeminiServerHandleAdapterExistsInProject.class,
			OllamaServerHandleAdapterExistsInProject.class
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
