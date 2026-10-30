package gradingTools.comp690f26.assignment1.tracedClassesSuite;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.common.AbstractClassFoundInTrace;
import util.annotations.Explanation;

// Optional role: keep its credit status when grouped with required roles.
@util.annotations.IsExtra(true)
@util.annotations.MaxValue(0)
@Explanation("Derives the role from accumulation evidence; supplies class names for project lookup.")
public class StreamChunkMergerFoundInTrace extends AbstractClassFoundInTrace {
	@Override
	protected String getTag() {
		return Assignment1Tags.STREAM_CHUNK_MERGER;
	}
}
