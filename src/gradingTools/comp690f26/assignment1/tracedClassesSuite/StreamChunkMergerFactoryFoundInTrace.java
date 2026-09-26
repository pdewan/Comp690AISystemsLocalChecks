package gradingTools.comp690f26.assignment1.tracedClassesSuite;
import util.annotations.Explanation;

import gradingTools.comp690f26.assignment1.Assignment1Tags;

@Explanation("Derives the role from merger-factory-fetch evidence; supplies class names for project lookup.")
public class StreamChunkMergerFactoryFoundInTrace
		extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.STREAM_CHUNK_MERGER_FACTORY;
	}
}
