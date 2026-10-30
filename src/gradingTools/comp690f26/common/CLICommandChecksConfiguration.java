package gradingTools.comp690f26.common;

import eduAI.trace.processors.cli.*;
import java.util.Objects;
import java.util.function.Supplier;

/** Explicit registered rules and required inputs supplied by a CLI assignment suite.
 * Session-dependent rules are prepared only by the check that consumes them. */
public record CLICommandChecksConfiguration(Supplier<CLICommandResultCheckerRegistry> traceRules,
        CLICommandExpectations expectedInputs) {
    public CLICommandChecksConfiguration { Objects.requireNonNull(traceRules); Objects.requireNonNull(expectedInputs); }
}
