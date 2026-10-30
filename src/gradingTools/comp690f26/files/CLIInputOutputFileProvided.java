package gradingTools.comp690f26.files;

import grader.basics.junit.*;
import grader.basics.project.*;
import grader.basics.testcase.PassFailJUnitTestCase;
import java.nio.file.*;


/** Only checks the configured file's existence/readability. A readable malformed
 * log passes here and fails syntax separately; a missing file earns no credit. */
@util.annotations.Explanation("Requires a readable CLI input/output file; does not parse or validate its contents.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.EVIDENCE_GATE)
public class CLIInputOutputFileProvided extends PassFailJUnitTestCase {
    private Path interactionsFile;
    @Override public TestCaseResult test(Project project,boolean interactive) {
        var source=FilesProvidedSupport.configuration().interactions().orElseThrow(
            ()->new IllegalStateException("CLI input/output file must be configured"));
        interactionsFile=FilesProvidedSupport.resolvedDirectory().resolve(source.fileName());
        return Files.isRegularFile(interactionsFile) && Files.isReadable(interactionsFile) ? pass()
            : fail("Required readable CLI input/output file not found: "+interactionsFile.toAbsolutePath());
    }
    public Path getInteractionsFile() {
        if(interactionsFile==null) throw new IllegalStateException("File check has not run");
        return interactionsFile;
    }
}
