package gradingTools.comp690f26.assignment1.tracedClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.common.AbstractClassFoundInTrace;
import util.annotations.Explanation;

@Explanation("Derives the role from request evidence; supplies class names for project lookup.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(0)
public class ParameterStoreFoundInTrace extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.PARAMETER_STORE;
	}
}
