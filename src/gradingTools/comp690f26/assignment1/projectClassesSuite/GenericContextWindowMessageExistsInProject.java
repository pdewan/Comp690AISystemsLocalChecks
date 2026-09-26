package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GenericContextWindowMessageFoundInTrace;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class GenericContextWindowMessageExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{TraceFilesProvided.class,
				GenericContextWindowMessageFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.GENERIC_CONTEXT_WINDOW_MESSAGE;
	}
}
