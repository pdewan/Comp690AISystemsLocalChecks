package gradingTools.comp690f26.assignment1.commonInterfacesSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiResponseMessageAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaResponseMessageAdapterExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.util.List;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Structural credit is independent: shared interfaces pass; disjoint interfaces fail without vetoing value comparisons. */
@Explanation("Requires one shared inherited interface after project lookup; graded independently of values.")
@MaxValue(1)
public class NativeResponseMessageAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			GeminiResponseMessageAdapterExistsInProject.class,
			OllamaResponseMessageAdapterExistsInProject.class
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
