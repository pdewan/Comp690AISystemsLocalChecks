package gradingTools.comp690f26.assignment2.physicalStructureSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;

/** Field-type paths from registries to handler interfaces and from observables
 * to observer interfaces. Required paths pass; missing paths fail. */
@Explanation("Checks registry-to-handler and observable-to-observer instance-field types.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2CommandRegistryHandlerTypes.class,
    A2PropertyRegistryHandlerTypes.class,
    A2DispatcherObserverTypes.class,
    A2StreamObservableObserverTypes.class
})
public class A2PhysicalStructureSuite { }
