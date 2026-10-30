package gradingTools.comp690f26.assignment2.observerSuite;

import gradingTools.comp690f26.assignment2.interfacesSuite.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/**
 * Groups both observer contracts, observable registration APIs, role
 * separation, and notification/delivery checks. Valid interfaces and relationships
 * with matching deliveries pass. Missing methods,
 * shared processing/display identities or mismatched deliveries fail. Missing
 * prerequisite evidence blocks dependent checks. Reflection checks API shape;
 * traces and captured output establish runtime consistency. Field-type checks
 * belong to A2PhysicalStructureSuite and remain delivery prerequisites.
 */
@Explanation("Checks command and stream observer contracts, observable methods, separation and delivery.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2ObserverNotificationMethods.class,
    A2DispatcherObserverMethods.class,
    A2StreamObservableObserverMethods.class,
    ObserverRolesSeparated.class,
    A2CLICommandObserverOutput.class,
    A2CLIStreamObserverOutput.class
})
public class A2ObserverObservableSuite { }
