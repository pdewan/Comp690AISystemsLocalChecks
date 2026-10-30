package gradingTools.comp690f26.files;

import eduAI.trace.processors.cli.CLIExpectedContentChecks;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import util.annotations.Explanation;
import util.annotations.MaxValue;

/** Supplies parsed inputs and explicit attachment kinds, without reading files.
 * Consumers prepare only their content kind. A missing text file fails prompt
 * comparison but cannot veto image comparison. Missing parsed inputs fails here. */
@Explanation("Supplies parsed request inputs and attachment-kind policy; consuming checks read only their relevant files.")
@MaxValue(0)
public class A2CLIRequestExpectationsProvided extends PassFailJUnitTestCase implements gradingTools.comp690f26.common.ExpectedContentChecksProvider {
    private CLIExpectedContentChecks expectations;
    protected Class[] precedingTests() { return new Class[]{CLIOuterOutputSyntax.class}; }
    public TestCaseResult test(Project project,boolean interactive) {
        var inputs=(CLIOuterOutputSyntax)getFirstPrecedingTestInstance();
        expectations=CLIExpectedContentChecks.forSession(inputs.getInteractions().orElseThrow(),
            inputs.getInteractionsFile().toAbsolutePath().getParent(),FilesProvidedSupport.configuration().checks().attachmentKinds());
        return pass();
    }
    public eduAI.trace.processors.ExpectedContentChecks getExpectedContentChecks() {
        return java.util.Objects.requireNonNull(expectations,"Request-input preparation has not succeeded");
    }
}
