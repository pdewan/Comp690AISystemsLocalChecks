package gradingTools.comp690f26.common.commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/**
 * Groups command result semantics. Results consistent with property returns,
 * model responses and prior session history pass; missing or changed expected
 * values fail. Insufficient history earns no credit in the relevant leaf.
 * Handler registration and separation have their own top-level A2 suites.
 */
@Explanation("Checks property, prompt, transcript and messages results against observed actions and history.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptivePropertyCommandResultsMatch.class,
    AdaptivePromptCommandResultsMatch.class,
    AdaptiveTranscriptCommandResultsMatch.class,
    AdaptiveOrdinaryMessagesCommandResultsMatch.class,
    AdaptiveSystemMessagesCommandResultsMatch.class
})
public class A2CommandResultsSuite { }
