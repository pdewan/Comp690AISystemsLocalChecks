package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.ServerHandleFactoryRegistryFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")

public class ServerHandleFactoryRegistryExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{AdaptiveTraceFilesProvided.class,
				ServerHandleFactoryRegistryFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.SERVER_HANDLE_FACTORY_REGISTRY;
	}
}
