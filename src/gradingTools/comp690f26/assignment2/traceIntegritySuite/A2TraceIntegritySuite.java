package gradingTools.comp690f26.assignment2.traceIntegritySuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;

@Explanation("Requires the CLI trace events introduced in A2; detailed behavior is checked separately.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CLICommandHandledTraced.class,
    CLIPropertyHandledTraced.class,
    CLIParameterHistoryUpdatedTraced.class,
    CLICommandOutputNotifiedTraced.class,
    CLIStreamOutputNotifiedTraced.class,
    CLICommandDisplayedTraced.class,
    CLIStreamDisplayedTraced.class,
    CLIFileExpandedTraced.class
})
public class A2TraceIntegritySuite {
    public static Class<?>[] checks() {
        return A2TraceIntegritySuite.class.getAnnotation(Suite.SuiteClasses.class).value();
    }
}
