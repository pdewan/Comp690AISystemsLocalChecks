package gradingTools.comp690f26.assignment1.contextHistorySuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/**
 * Groups the two existing A1 compaction checks, at ten points each. Compaction
 * matching the provider's A1 request expectations passes; missing or incorrect
 * compaction fails. Missing required evidence earns no credit under the leaf
 * checks. The ordinary retention checks are in the shared ContextHistorySuite.
 */
@Explanation("Checks A1 context compaction for Gemini and Ollama; two checks at 10 points each.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A1GeminiExpectedContextWindowCompaction.class,
    A1OllamaExpectedContextWindowCompaction.class
})
@MaxValue(A1ContextCompactionSuite.POINTS)
public class A1ContextCompactionSuite {
    public static final int POINTS = 20;
}
