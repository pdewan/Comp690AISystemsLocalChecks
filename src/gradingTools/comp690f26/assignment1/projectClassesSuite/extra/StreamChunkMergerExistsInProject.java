package gradingTools.comp690f26.assignment1.projectClassesSuite.extra;
import util.annotations.Explanation;
import util.annotations.IsExtra;


import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AbstractClassExistsInProject;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFoundInTrace;

@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class StreamChunkMergerExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{TraceFilesProvided.class,
				StreamChunkMergerFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.STREAM_CHUNK_MERGER;
	}
}
