package gradingTools.comp690f26.assignment2.suites;

import gradingTools.comp690f26.common.commands.CommandHandlerInstancesReused;
import gradingTools.comp690f26.common.commands.CommandHandlersRegistered;
import gradingTools.comp690f26.common.properties.PropertyHandlerInstancesReused;
import gradingTools.comp690f26.common.properties.PropertyHandlersRegistered;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/**
 * Groups command and property registry associations and instance reuse.
 * A stable registered handler passes; a wrong mapping or replaced handler fails.
 * Missing evidence earns no credit in the affected leaf check.
 */
@Explanation("Checks command and property registration and handler reuse; no result-content comparisons.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CommandHandlersRegistered.class,
    CommandHandlerInstancesReused.class,
    PropertyHandlersRegistered.class,
    PropertyHandlerInstancesReused.class
})
public class A2HandlerRegistrationSuite { }
