package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyChoicesChecks;

/**
 * Checks every observed current_models result for the model choices explicitly
 * required by the suite. Each requirement specifies either an exact name or a
 * prefix; at least one returned alternative must satisfy each requirement.
 * Prefix matching is case-sensitive and applies at the start of the name.
 * An exact-name mismatch is never retried as a prefix match.
 *
 * Success example: configured prefixes gemini- and llava are satisfied by
 * [gemini-example, llava:example]. With an exact gemini-example requirement,
 * gemini-example-new fails. Missing llava or x-gemini-example also fails the
 * prefix example. These examples do not prescribe model versions.
 * No model result or missing/ambiguous choices-path evidence earns no credit.
 * The check proves advertised choices, not model availability or support.
 */
@util.annotations.Explanation("Checks that every current_models result includes a choice matching each suite-configured exact name or prefix. Missing required choices or missing/ambiguous evidence fails. Prefix matches do not establish model availability or support.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROPERTY_DESCRIPTION)
public class RequiredModelAlternatives extends AbstractPropertyChoicesCheck {
    @Override protected CLIPropertyChoicesChecks.Check rule() { return CLIPropertyChoicesChecks.Check.MODEL_CHOICES; }
}
