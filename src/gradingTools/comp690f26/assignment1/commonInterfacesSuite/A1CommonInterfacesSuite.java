package gradingTools.comp690f26.assignment1.commonInterfacesSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


@MaxValue(12)
@Explanation("Requires one shared interface per adapter role; assessed independently of behavior.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    NativeServerHandleAdapters.class,
    NativePromptMessageAdapters.class,
    NativeResponseMessageAdapters.class,
    NativeParameterAdapters.class,
    NativeResponseMetadataAdapters.class,
    NativeChunkCallbackAdapters.class,
})
public class A1CommonInterfacesSuite {
}
