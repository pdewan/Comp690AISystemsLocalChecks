package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Infers a choices collection from flattened results for current_models, using
 * model names observed in the traced property values. Exactly one stable candidate
 * path must contain all observed selections. The same path must be available in
 * the other property results. Repeated choices for a property must agree, and each
 * selected value must belong to its nonempty choices. Empty choices are allowed
 * for properties with no listed alternatives.
 *
 * Success example: current_models selects modelA and later modelB; both results
 * contain choices [modelA, modelB], and other results expose that same choices
 * path with their own applicable alternatives.
 * Failure examples: modelB is missing; a property's choices change; the choices
 * field is absent; or two fields are equally plausible candidates.
 * No model-selection evidence or no repeated nonempty choices means no credit.
 * This does not require two selected models, prescribe field names, or prove that
 * every model available from the provider is listed.
 */
@util.annotations.Explanation("Finds one stable choices field containing the traced model selections, checks that path in other results, and checks repeated choices and selected-value membership. Requires model evidence and repeated nonempty choices. This does not prove that all available models are listed.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PROPERTY_DESCRIPTION)
public class PropertyAlternativesConsistent extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.ALTERNATIVES; }
}
