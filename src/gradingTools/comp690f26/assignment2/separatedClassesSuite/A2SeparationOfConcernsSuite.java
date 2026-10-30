package gradingTools.comp690f26.assignment2.separatedClassesSuite;

import gradingTools.comp690f26.assignment2.observerSuite.A2ObserverObservableSuite;
import gradingTools.comp690f26.common.GradingCheckWeights;
import gradingTools.comp690f26.common.properties.PropertyHandlerInstancesSeparated;
import gradingTools.comp690f26.common.properties.PropertyStoreIdentityConsistent;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/**
 * Groups class, property-handler instance, file-expander separation.
 * Its twelve checks each retain an explicit five-point weight.
 * Separate required classes and objects pass; shared property handlers, one
 * text/image expander fail. Missing role
 * evidence earns no credit in the affected leaf check.
 * Also checks the single property-store identity and the overall structural
 * trace contract. Observer separation and delivery belong to A2ObserverObservableSuite.
 */
@MaxValue(GradingCheckWeights.SEPARATION_OF_CONCERNS_SUITE)
@Explanation("Checks separate CLI classes and distinct property handlers, text/image expanders and the shared property store.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CLICommandDispatcherIsSeparated.class,
    CommandHandlerRegistryIsSeparated.class,
    PromptCommandHandlerIsSeparated.class,
    PropertyCommandHandlerIsSeparated.class,
    TranscriptCommandHandlerIsSeparated.class,
    MessagesCommandHandlerIsSeparated.class,
    ExitCommandHandlerIsSeparated.class,
    CLIPropertyHandlersAreSeparated.class,
    PropertyHandlerInstancesSeparated.class,
    A2CLISeparateFileExpanders.class,
    PropertyStoreIdentityConsistent.class,
    A2CLIStructureContract.class
})
public class A2SeparationOfConcernsSuite { }
