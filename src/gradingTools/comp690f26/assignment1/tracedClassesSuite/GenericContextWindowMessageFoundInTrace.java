package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;

@Explanation("Derives the role from message-translation evidence; supplies class names for project lookup.")
public class GenericContextWindowMessageFoundInTrace
		extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.GENERIC_CONTEXT_WINDOW_MESSAGE;
	}
}
