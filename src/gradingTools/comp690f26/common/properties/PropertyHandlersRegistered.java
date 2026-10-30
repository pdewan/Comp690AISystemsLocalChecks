package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks the registry evidence in every propertyHandled return, using trace history
 * only. The property key must map to the returning handler's identity in the
 * registry contents printed in that event. Caller, handler and registry identities
 * must be present, and the printed registry identity must match its contents.
 * Repeated uses of a property must keep its registry; different properties may use
 * different registries. Lazy registration is allowed.
 *
 * Success example: timeout maps to Handler@a in Registry@b, and the timeout return
 * names Handler@a. A later timeout return uses Registry@b with a matching entry.
 * Failure examples: timeout maps to Handler@a but Handler@c returns; the key is
 * missing; or a later timeout operation uses a different registry.
 * No property returns means insufficient evidence and no credit. This proves the
 * recorded association, not that the implementation actually performed a lookup.
 */
@util.annotations.Explanation("Checks that each traced property key maps to the returning handler in the printed registry. Repeated uses of a property must keep the same registry; different properties may use different registries. Missing or mismatched evidence fails.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REGISTRATION)
public class PropertyHandlersRegistered extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.REGISTRATION; }
}
