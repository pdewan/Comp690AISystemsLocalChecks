package gradingTools.comp690f26.assignment1.projectClassesSuite.extra;

import gradingTools.comp690f26.assignment1.Assignment1Tags;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFactoryRegistryFoundInTrace;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import util.annotations.Explanation;

// Optional role: keep its credit status when grouped with required roles.
@util.annotations.IsExtra(true)
@util.annotations.MaxValue(2)
@Explanation("After role discovery, loads every mapped project class for reflection and the global integrity gate.")
public class StreamChunkMergerFactoryRegistryExistsInProject
		extends AbstractClassExistsInProject {
	@Override
	protected Class[] precedingTests() {
		return new Class[]{AdaptiveTraceFilesProvided.class,
				StreamChunkMergerFactoryRegistryFoundInTrace.class};
	}

	@Override
	protected String getTag() {
		return Assignment1Tags.STREAM_CHUNK_MERGER_FACTORY_REGISTRY;
	}
}
