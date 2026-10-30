package gradingTools.comp690f26.files;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


@Explanation("Requires the CLI input/output log and trace files configured by the assignment suite.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CLIInputOutputFileProvided.class,
    AdaptiveTraceFilesProvided.class
})
public class A2FilesProvidedSuite { }
