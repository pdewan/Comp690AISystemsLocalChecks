package gradingTools.comp690f26.assignment1.tracedClassesSuite;

import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.annotations.Explanation;
import util.annotations.MaxValue;
import util.trace.Tracer;

@Explanation("Derives role-to-class mappings from events; project lookup then verifies those classes exist.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    StreamChunkMergerFoundInTrace.class,
    StreamChunkMergerFactoryFoundInTrace.class,
    StreamChunkMergerFactoryRegistryFoundInTrace.class,
    GenericContextWindowMessageFoundInTrace.class,
    ParameterStoreFoundInTrace.class,
    GeminiServerHandleAdapterFoundInTrace.class,
    OllamaServerHandleAdapterFoundInTrace.class,
    GeminiServerHandleFactoryFoundInTrace.class,
    OllamaServerHandleFactoryFoundInTrace.class,
    GeminiPromptMessageAdapterFoundInTrace.class,
    OllamaPromptMessageAdapterFoundInTrace.class,
    GeminiResponseMessageAdapterFoundInTrace.class,
    OllamaResponseMessageAdapterFoundInTrace.class,
    GeminiParameterAdapterFoundInTrace.class,
    OllamaParameterAdapterFoundInTrace.class,
    GeminiResponseMetadataAdapterFoundInTrace.class,
    OllamaResponseMetadataAdapterFoundInTrace.class,
    GeminiChunkCallbackAdapterFoundInTrace.class,
    OllamaChunkCallbackAdapterFoundInTrace.class,
    ServerHandleFactoryRegistryFoundInTrace.class,
})
@MaxValue(0)
public class A1TracedClassesSuite {
	 
	
}
