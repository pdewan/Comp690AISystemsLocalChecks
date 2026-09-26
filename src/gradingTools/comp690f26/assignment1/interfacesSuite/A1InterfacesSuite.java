package gradingTools.comp690f26.assignment1.interfacesSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import util.annotations.Explanation;
import util.annotations.MaxValue;
@Explanation("Reflects on loaded classes for interface use; distinct from cross-provider compatibility.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AllTaggedClassesImplementSingleInterface.class,
	TaggedClassPhysicalStructuresUseInterfaces.class,
})
public class A1InterfacesSuite {
}
