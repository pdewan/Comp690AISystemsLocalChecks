package gradingTools.comp690f26.assignment1.projectClassesSuite;
import util.annotations.Explanation;
import gradingTools.comp690f26.assignment1.*;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.OllamaServerHandleFactoryFoundInTrace;
import util.annotations.MaxValue;
@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class OllamaServerHandleFactoryExistsInProject extends AbstractClassExistsInProject { protected Class[] precedingTests(){return new Class[]{TraceFilesProvided.class,OllamaServerHandleFactoryFoundInTrace.class};} protected String getTag(){return Assignment1Tags.OLLAMA_SERVER_HANDLE_FACTORY;} }
