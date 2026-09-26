package gradingTools.comp690f26.assignment1.crossProviderSuite;

import util.annotations.Explanation;
import util.annotations.MaxValue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Explanation("Compares canonical non-streaming structure after native-to-generic matches; not identical replies.")
@RunWith(Suite.class)
@Suite.SuiteClasses({ GenericRequestStructuresMatchAcrossProviders.class,
		GenericResponseStructuresMatchAcrossProviders.class })
@MaxValue(50)
public class A1CrossProviderSuite {
}
