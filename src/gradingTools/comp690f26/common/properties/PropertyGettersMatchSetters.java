package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks traced getters against the most recent preceding setter for the same
 * property. The trace-history reader identifies a setter by the existing
 * historyWithParametersUpdated marker before the command return. This check does
 * not read the input script or assume a particular property value.
 *
 * Success example: set timeout to 120, get 120, set timeout to 240, then get 240.
 * Failure example: the final getter still reports 120. A missing traced value
 * also fails.
 * A getter with no preceding setter provides no comparison. If there are no
 * setter/getter pairs at all, the check awards no credit. If qualifying pairs
 * exist, every such pair must agree. Agreement with what the user actually typed
 * is checked separately by AdaptiveExpectedPropertyInputs.
 * Store identity and accumulated contents must pass first through
 * PropertyStoreConsistent. Correct getter returns cannot earn credit when the
 * store has lost a previously established setting.
 */
@util.annotations.Explanation("Requires consistent store contents, then compares each traced getter with the latest preceding setter for that property, without reading user input. Missing values or disagreements fail; no qualifying setter/getter pair means no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.VALUE_CONSISTENCY)
public class PropertyGettersMatchSetters extends AbstractPropertyConsistencyCheck {
    @Override protected Class<?>[] additionalPrecedingTests() { return new Class<?>[]{PropertyStoreConsistent.class}; }
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.GETTERS; }
}
