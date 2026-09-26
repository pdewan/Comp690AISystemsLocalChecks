package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;

@Explanation("Derives the role from factory-fetch evidence; supplies class names for project lookup.")
public class ServerHandleFactoryRegistryFoundInTrace
		extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.SERVER_HANDLE_FACTORY_REGISTRY;
	}
}
