package gradingTools.comp690f26.assignment1.style;

import grader.basics.junit.BasicJUnitUtils;
import gradingTools.comp690f26.assignment1.A1RequiredClassesDefined;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.IsExtra;

@Explanation("Uses source-analysis ratios for interface types, constants, and overridden public methods.")
@RunWith(Suite.class)
@IsExtra(true)
@Suite.SuiteClasses({
    A1RequiredClassesDefined.class,
    A1InterfaceAsType.class,
    A1NamedConstants.class,
    A1PublicMethodsOverride.class
//	InterpolatedReuse.class,
    //	UtilityJavaDocs.class
})
public class GeneralStyleSuite {
	

}
