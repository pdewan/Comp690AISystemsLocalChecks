package gradingTools.comp690f26.common;

import eduAI.trace.processors.ExpectedContentChecks;

/** A prerequisite that supplies the suite's configured content checks. */
public interface ExpectedContentChecksProvider {
    ExpectedContentChecks getExpectedContentChecks();
}
