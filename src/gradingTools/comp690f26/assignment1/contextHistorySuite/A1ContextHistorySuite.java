package gradingTools.comp690f26.assignment1.contextHistorySuite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@Explanation("After translation checks, follows retained text and images across turns and compaction.")
@RunWith(Suite.class) @Suite.SuiteClasses({
	GeminiPreviousSystemPromptsRetained.class,OllamaPreviousSystemPromptsRetained.class,
	GeminiPreviousUserPromptsRetained.class,OllamaPreviousUserPromptsRetained.class,
	GeminiPreviousImagesRetained.class,OllamaPreviousImagesRetained.class,
	GeminiPreviousCompletedResponsesRetained.class,OllamaPreviousCompletedResponsesRetained.class,
	GeminiExpectedContextWindowCompaction.class,OllamaExpectedContextWindowCompaction.class})
@MaxValue(100)
public class A1ContextHistorySuite {}
