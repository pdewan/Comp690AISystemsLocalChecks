package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks that every repeated property uses the same handler object, using only
 * identities recorded in propertyHandled events. At least one property must occur
 * more than once. Registration and separation from other properties have their own
 * checks.
 *
 * Success example: setting timeout and later getting timeout both name Handler@a.
 * Failure example: the later timeout operation names Handler@b, even if a and b
 * have the same Java class. A missing handler identity also fails.
 * If every property appears only once, reuse cannot be established: no credit.
 */
@util.annotations.Explanation("Checks that repeated operations on a property use the same handler object. A changed or missing identity fails; no repeated property means insufficient evidence and no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REGISTRATION)
public class PropertyHandlerInstancesReused extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.HANDLER_REUSE; }
}
