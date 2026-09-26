package gradingTools.comp690f26.assignment1.style;
import util.annotations.Explanation;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import gradingTools.basics.sharedTestCase.checkstyle.NamedConstantsRatioCheck;
import gradingTools.comp690f26.assignment1.A1RequiredClassesDefined;
import gradingTools.basics.sharedTestCase.checkstyle.VariableHasClassTypeRatioCheck;

import util.annotations.IsExtra;
import util.annotations.MaxValue;
@Explanation("Uses source-analysis ratios to discourage concrete variable types, not runtime value types.")
@MaxValue(1)
public class A1InterfaceAsType extends  VariableHasClassTypeRatioCheck{
	 public A1InterfaceAsType() {
		 super();
	 }
	
	 protected Class taggedClassesDefined() {
		 return A1RequiredClassesDefined.class;
//			return A7TaggedClassesDefined.class;
		}
	
	public TestCaseResult test(Project aProject, boolean autoGrade) throws NotAutomatableException, NotGradableException {
	        
			TestCaseResult aSuperResult = super.test(aProject, autoGrade);
			return aSuperResult;
//			A7TaggedClassesDefined aTaggedClassesDefined = (A7TaggedClassesDefined) JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(A7TaggedClassesDefined.class);
//			return aTaggedClassesDefined.computeResultBasedOnTaggedClasses(aSuperResult);
	        
	    }
}

