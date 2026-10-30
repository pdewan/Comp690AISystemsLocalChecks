package gradingTools.comp690f26.assignment2.suites;

import gradingTools.comp690f26.assignment2.semanticsSuite.A2SemanticsSuite;
import gradingTools.comp690f26.common.commands.AdaptiveExpectedCommandInputs;
import gradingTools.comp690f26.common.properties.AdaptiveExpectedPropertyInputs;
import gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;


/**
 * Checks required CLI content: operations, property values, attachment names,
 * and their order, while deriving request expectations from the actual input.
 * The zero-point preparation check derives values from actual inputs and
 * attachments; it fails if that evidence cannot be interpreted. It does not
 * compare requests. Request comparisons remain in A2SemanticsSuite.
 * The property and command
 * input checks trigger their consistency prerequisites first. The command
 * finale also requires message-output grammar and the semantic trace contract:
 * correct inputs with malformed messages or inconsistent trace semantics fail.
 * These checks receive visible grades through ordinary prerequisites. Required
 * operations and inputs pass; omitted or incorrect requirements fail.
 * Consistency checks can depend on expectation preparation, but do not depend
 * on the checks that enforce the required assignment-input sequence.
 * Independent assessments remain separately selectable; they do not block
 * credit for otherwise valid inputs or cause unrelated checks to execute.
 * Missing evidence earns no credit in the affected leaf check.
 */
@Explanation("Checks required CLI operations, property values, attachments and their order, requiring output syntax, semantic consistency and relevant A1 prerequisites with visible grades. Independent assessments remain separately selectable.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2CLIRequestExpectationsProvided.class,
    AdaptiveExpectedPropertyInputs.class,
    AdaptiveExpectedCommandInputs.class
})
public class A2ExpectedContentSuite { }
