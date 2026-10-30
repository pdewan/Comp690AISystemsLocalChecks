package gradingTools.comp690f26.combined;

import gradingTools.comp690f26.assignment1.interfacesSuite.AllTaggedClassesImplementSingleInterface;
import gradingTools.comp690f26.assignment1.interfacesSuite.TaggedClassPhysicalStructuresUseInterfaces;
import gradingTools.comp690f26.assignment2.interfacesSuite.A2AllTaggedClassesImplementSingleInterface;
import gradingTools.comp690f26.assignment2.interfacesSuite.A2TaggedFieldsUseInterfaces;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Combines existing atomic checks for both assignments. Original suites remain
 * available separately; this grouping adds no dependencies or test behavior.
 * Explicit leaf scores and this suite's remaining allocation preserve the rubric. */
@Explanation("Combines the A1 and A2 Interfaces checks with their existing points and prerequisites.")
@MaxValue(30)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    AllTaggedClassesImplementSingleInterface.class,
    TaggedClassPhysicalStructuresUseInterfaces.class,
    A2AllTaggedClassesImplementSingleInterface.class,
    A2TaggedFieldsUseInterfaces.class
})
public class A1A2InterfacesSuite { }
