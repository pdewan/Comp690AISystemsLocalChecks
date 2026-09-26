package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;

@Explanation("Derives the role from request evidence; supplies class names for project lookup.")
public class ParameterStoreFoundInTrace extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.PARAMETER_STORE;
	}
}
