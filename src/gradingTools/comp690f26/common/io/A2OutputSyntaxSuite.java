package gradingTools.comp690f26.common.io;

import gradingTools.comp690f26.common.GradingCheckWeights;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/**
 * Checks outer framing and command-specific output syntax. The suite
 * distributes its points equally among the four syntax checks.
 */
@MaxValue(GradingCheckWeights.OUTPUT_SYNTAX_SUITE)
@Explanation("Checks outer framing and command-specific output syntax.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CLIOuterOutputSyntax.class,
    CLIPropertyOutputSyntax.class,
    CLITranscriptOutputSyntax.class,
    CLIMessagesOutputSyntax.class
})
public class A2OutputSyntaxSuite {
}
