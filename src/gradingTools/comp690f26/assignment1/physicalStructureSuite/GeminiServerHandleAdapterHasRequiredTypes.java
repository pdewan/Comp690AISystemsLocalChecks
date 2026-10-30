package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import com.google.genai.Client;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandlesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceProviderNamesConsistent;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.util.List;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After handle evidence and project lookup, follows field types to the native client.")
public class GeminiServerHandleAdapterHasRequiredTypes
		extends AbstractClassHasRequiredTypes {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			TraceProviderNamesConsistent.class,
			ServerHandlesFetchedTraced.class,
			GeminiServerHandleAdapterExistsInProject.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected List<Class<?>> getRequiredTypes() {
		return List.of(Client.class);
	}
}
