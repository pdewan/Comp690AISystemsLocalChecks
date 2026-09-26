package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.ParameterStoreFoundInTrace;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class ParameterStoreExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{TraceFilesProvided.class,
				ParameterStoreFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.PARAMETER_STORE;
	}
}
