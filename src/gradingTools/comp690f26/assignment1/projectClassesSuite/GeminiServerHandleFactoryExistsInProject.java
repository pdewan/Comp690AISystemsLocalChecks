package gradingTools.comp690f26.assignment1.projectClassesSuite;

import gradingTools.comp690f26.assignment1.*;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.GeminiServerHandleFactoryFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class GeminiServerHandleFactoryExistsInProject extends AbstractClassExistsInProject { protected Class[] precedingTests(){return new Class[]{AdaptiveTraceFilesProvided.class,GeminiServerHandleFactoryFoundInTrace.class};} protected String getTag(){return Assignment1Tags.GEMINI_SERVER_HANDLE_FACTORY;} }
