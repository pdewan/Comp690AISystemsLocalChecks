package gradingTools.comp690f26.assignment2.parameterHistorySuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


@Explanation("Checks that property setters preserve parameter snapshots for earlier requests.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2CLIParameterHistorySnapshotsPreserved.class
})
public class A2ParameterHistorySuite { }
