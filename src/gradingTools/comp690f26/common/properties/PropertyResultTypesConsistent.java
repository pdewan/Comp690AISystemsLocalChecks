package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks type information inside flattened property result objects. The suite's
 * property contracts define the type groups, but no input values or I/O file are
 * used. Infer one common field path whose label is stable within each type group
 * and different between groups. Result identities must be valid and must not be
 * the handler identity.
 *
 * Success example: timeout and top_k results use a student-chosen field category
 * with value count, while stream uses category with value flag. The field and
 * label names are examples, not requirements.
 * Failure examples: timeout uses count but top_k uses number; stream also uses
 * count; two fields are equally plausible type fields; or the result is missing.
 * At least two type groups and two distinct properties sharing one type are
 * needed. Only timeout and stream, for example, provide insufficient evidence
 * for agreement between different properties of the same type and earn no credit.
 */
@util.annotations.Explanation("Uses configured property types to find one result field with consistent labels within each type group and different labels between groups. Requires multiple groups and distinct properties sharing a type; missing, ambiguous or inconsistent evidence earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROPERTY_DESCRIPTION)
public class PropertyResultTypesConsistent extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.RESULT_TYPES; }
}
