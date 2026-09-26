package gradingTools.comp690f26.assignment1.physicalStructureSuite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Explanation("Follows declared instance-field types to native clients; gates expected-content checks.")

@RunWith(Suite.class)
@Suite.SuiteClasses({
	GeminiServerHandleAdapterHasRequiredTypes.class,
	OllamaServerHandleAdapterHasRequiredTypes.class,
})
@MaxValue(4)
public class A1PhysicalStructureSuite {
}
