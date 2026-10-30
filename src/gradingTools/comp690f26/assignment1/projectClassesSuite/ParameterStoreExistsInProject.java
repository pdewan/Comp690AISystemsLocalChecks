package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.ParameterStoreFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class ParameterStoreExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{AdaptiveTraceFilesProvided.class,
				ParameterStoreFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.PARAMETER_STORE;
	}
}
