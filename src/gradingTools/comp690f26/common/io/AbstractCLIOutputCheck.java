package gradingTools.comp690f26.common.io;

import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import grader.basics.project.*;
import grader.basics.testcase.PassFailJUnitTestCase;
import java.util.*;

/** I/O-only checking: prerequisites supply stdout, never a trace processor. */
public abstract class AbstractCLIOutputCheck extends PassFailJUnitTestCase {
    protected abstract List<String> check(CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException;
    @Override protected Class[] precedingTests() { return new Class[]{CLIOuterOutputSyntax.class}; }
    @Override public TestCaseResult test(Project project,boolean interactive) {
        CLIOuterOutputSyntax syntax=null;
        for(var prerequisite:getPrecedingTestInstances()) if(prerequisite instanceof CLIOuterOutputSyntax parsed) syntax=parsed;
        if(syntax==null) throw new IllegalStateException("Outer syntax prerequisite did not run");
        try {
            var errors=check(syntax.getInteractions().orElseThrow(),syntax.getInteractionsFile().toAbsolutePath().getParent());
            return errors.isEmpty()?pass():fail(String.join("\n",errors));
        } catch(java.io.IOException | IllegalArgumentException error) { return fail(error.getMessage()); }
    }
}
