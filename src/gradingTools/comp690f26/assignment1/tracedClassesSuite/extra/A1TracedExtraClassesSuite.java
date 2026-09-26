package gradingTools.comp690f26.assignment1.tracedClassesSuite.extra;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFactoryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFactoryRegistryFoundInTrace;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.StreamChunkMergerFoundInTrace;
import util.annotations.Explanation;
import util.annotations.IsExtra;
import util.annotations.MaxValue;

@Explanation("Derives optional merger roles from evidence before looking up their project classes.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	StreamChunkMergerFoundInTrace.class,
	StreamChunkMergerFactoryFoundInTrace.class,
	StreamChunkMergerFactoryRegistryFoundInTrace.class,
})
@MaxValue(0)
@IsExtra(true)
public class A1TracedExtraClassesSuite {
}
