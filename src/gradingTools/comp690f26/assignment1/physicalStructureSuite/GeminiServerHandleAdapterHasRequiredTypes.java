package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import com.google.genai.Client;
import gradingTools.comp690f26.assignment1.projectClassesSuite.GeminiServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandlesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceProviderNamesConsistent;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.util.List;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Follows instance fields and uniquely implemented project interfaces to the native client.
 * A transport interface with one implementation holding that client passes. Missing clients,
 * unrelated implementations, and ambiguous static paths earn no credit unless the handle
 * trace supplies an actual runtime path. This does not execute student code. */
@Explanation("Finds the native client through instance fields, unique project interface implementations, or a traced runtime path; ambiguous static paths alone earn no credit.")
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
