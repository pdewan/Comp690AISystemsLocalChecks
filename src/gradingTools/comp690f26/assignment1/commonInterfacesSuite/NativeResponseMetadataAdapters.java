package gradingTools.comp690f26.assignment1.commonInterfacesSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiResponseMetadataAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaResponseMetadataAdapterExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.util.List;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Structural credit is independent: shared interfaces pass; disjoint interfaces fail without vetoing value comparisons. */
@Explanation("Requires one shared inherited interface after project lookup; graded independently of values.")
@MaxValue(1)
public class NativeResponseMetadataAdapters
		extends AbstractClassesImplementCommonInterface {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			GeminiResponseMetadataAdapterExistsInProject.class,
			OllamaResponseMetadataAdapterExistsInProject.class
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
