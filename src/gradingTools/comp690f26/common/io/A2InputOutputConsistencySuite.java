package gradingTools.comp690f26.common.io;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/** Checks captured output against actual inputs and earlier captured output without traces. Each check carries its own weight. */
@Explanation("Checks captured output against actual inputs and earlier captured output without traces.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AdaptiveStreamingOutputMatchesSetting.class,
    AdaptivePropertyOutputMatches.class,
    AdaptiveTranscriptOutputMatches.class,
    AdaptiveMessagesOutputMatches.class,
    AdaptiveSystemMessagesOutputMatches.class
})
public class A2InputOutputConsistencySuite { }
