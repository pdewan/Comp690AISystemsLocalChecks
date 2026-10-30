package gradingTools.comp690f26.assignment1.crossProviderSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


@Explanation("Compares canonical non-streaming structure after native-to-generic matches; not identical replies.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A1GenericRequestStructuresMatchAcrossProviders.class,
    A1GenericResponseStructuresMatchAcrossProviders.class
})
@MaxValue(50)
public class A1CrossProviderSuite {
}
