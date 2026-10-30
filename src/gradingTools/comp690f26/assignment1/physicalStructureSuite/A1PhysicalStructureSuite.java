package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Follows declared instance-field types to native clients; assessed independently of content.")

@RunWith(Suite.class)
@Suite.SuiteClasses({
    GeminiServerHandleAdapterHasRequiredTypes.class,
    OllamaServerHandleAdapterHasRequiredTypes.class,
})
@MaxValue(4)
public class A1PhysicalStructureSuite {
}
