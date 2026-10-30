package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks store identities across property returns, without comparing values.
 * Two observations of Store@a pass; Store@a followed by Store@b fails even if
 * their contents agree. Missing identity or fewer than two observations earns
 * no credit. Stored-value correctness is checked by PropertyStoreConsistent.
 */
@util.annotations.Explanation("Requires repeated property operations to use the same valid store identity; does not compare stored values.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class PropertyStoreIdentityConsistent extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.STORE_IDENTITY; }
}
