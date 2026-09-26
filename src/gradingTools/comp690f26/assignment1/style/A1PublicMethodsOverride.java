package gradingTools.comp690f26.assignment1.style;
import util.annotations.Explanation;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import gradingTools.comp690f26.assignment1.A1RequiredClassesDefined;
import gradingTools.basics.sharedTestCase.checkstyle.VariableHasClassTypeRatioCheck;
import gradingTools.basics.sharedTestCase.checkstyle.PublicMethodsOverrideRatioCheck;


import util.annotations.IsExtra;
import util.annotations.MaxValue;
@Explanation("Uses source-analysis ratios to favor public methods declared by an inherited contract.")
@MaxValue(1)
public class A1PublicMethodsOverride extends  PublicMethodsOverrideRatioCheck{
	 public A1PublicMethodsOverride() {
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

