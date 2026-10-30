package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyChoicesChecks;

/**
 * Uses the suite's type contracts to identify observed boolean properties.
 * Each result must contain exactly two distinct alternatives at the choices
 * path inferred from model results. Observed boolean values must belong to
 * those alternatives. No spelling such as true/false is prescribed here.
 *
 * Success example: a boolean property returns [on, off] and its traced value
 * is on. Failure examples: [on], [on, off, auto], [on, on], or a value auto
 * outside [on, off]. Empty choices also fail.
 * No boolean result, or missing/ambiguous choices-path evidence, earns no
 * credit. This checks returned evidence, not the text typed or printed.
 */
@util.annotations.Explanation("Checks that each observed boolean property returns exactly two distinct alternatives and that its traced value belongs to them. No true/false spelling is prescribed. Missing boolean results or missing/ambiguous choices evidence earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROPERTY_DESCRIPTION)
public class BooleanPropertyAlternatives extends AbstractPropertyChoicesCheck {
    @Override protected CLIPropertyChoicesChecks.Check rule() { return CLIPropertyChoicesChecks.Check.BOOLEAN_CHOICES; }
}
