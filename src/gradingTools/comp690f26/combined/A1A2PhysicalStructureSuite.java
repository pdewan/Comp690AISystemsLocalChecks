package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.assignment1.physicalStructureSuite.GeminiServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.OllamaServerHandleAdapterHasRequiredTypes;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.A2CommandRegistryHandlerTypes;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.A2DispatcherObserverTypes;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.A2PropertyRegistryHandlerTypes;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.A2StreamObservableObserverTypes;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 PhysicalStructure checks with their existing points and prerequisites.")
@MaxValue(12)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiServerHandleAdapterHasRequiredTypes.class,
    OllamaServerHandleAdapterHasRequiredTypes.class,
    A2CommandRegistryHandlerTypes.class,
    A2PropertyRegistryHandlerTypes.class,
    A2DispatcherObserverTypes.class,
    A2StreamObservableObserverTypes.class
})
public class A1A2PhysicalStructureSuite { }
