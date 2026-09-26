package gradingTools.comp690f26.assignment1.separatedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.*;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleFactoryExistsInProject;
import util.annotations.MaxValue;

@Explanation("After project lookup, rejects a class serving this role and any other trace-derived role.")
public class GeminiServerHandleFactoryIsSeparated extends AbstractClassIsSeparated {
	protected Class[] precedingTests() {
		return new Class[] { TraceFilesProvided.class, GeminiServerHandleFactoryExistsInProject.class };
	}

	protected String getTag() {
		return Assignment1Tags.GEMINI_SERVER_HANDLE_FACTORY;
	}
}
