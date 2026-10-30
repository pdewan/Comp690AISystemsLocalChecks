package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks the flattened store contents after each traced property
 * operation, without reading I/O. Remember values established by setters or first
 * observations; later setters may update their property. Each store observation
 * must retain all known property/value associations. At least two observations
 * are required, including a setter after observing another property, to establish
 * accumulated-value consistency. Repeating only getters earns no credit.
 *
 * Success example: after timeout=120 and temperature=0.7, the same Store@a contains
 * both settings. Changing timeout to 240 leaves temperature=0.7 intact.
 * Failure examples: temperature disappears or
 * changes while timeout is set; or a known value is stored under the wrong key.
 * One store observation is insufficient and earns no credit. Getter/setter return
 * agreement and preservation of earlier request snapshots have separate checks.
 * Requires PropertyStoreIdentityConsistent to pass first; changing store objects
 * earns no credit even when their contents agree.
 */
@util.annotations.Explanation("Requires consistent store identity and a setter after another property was observed. Checks retained settings; only getters or no preservation opportunity earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.STORE_CONSISTENCY)
public class PropertyStoreConsistent extends AbstractPropertyConsistencyCheck {
    @Override protected Class<?>[] additionalPrecedingTests() { return new Class<?>[]{PropertyStoreIdentityConsistent.class}; }
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.STORE; }
}
