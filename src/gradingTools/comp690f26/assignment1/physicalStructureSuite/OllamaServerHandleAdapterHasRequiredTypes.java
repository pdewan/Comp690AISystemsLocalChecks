package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.OllamaServerHandleAdapterExistsInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandlesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.TraceProviderNamesConsistent;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import io.github.ollama4j.Ollama;
import java.util.List;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After handle evidence and project lookup, follows field types to the native client.")
public class OllamaServerHandleAdapterHasRequiredTypes
		extends AbstractClassHasRequiredTypes {
	private static final Class[] PRECEDING_TESTS = {
			AdaptiveTraceFilesProvided.class,
			TraceProviderNamesConsistent.class,
			ServerHandlesFetchedTraced.class,
			OllamaServerHandleAdapterExistsInProject.class
	};

	@Override
	protected Class[] precedingTests() {
		return PRECEDING_TESTS;
	}

	@Override
	protected List<Class<?>> getRequiredTypes() {
		return List.of(Ollama.class);
	}
}
