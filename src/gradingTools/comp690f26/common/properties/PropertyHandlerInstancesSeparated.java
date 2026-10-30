package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.cli.CLIPropertyConsistencyChecks;

/**
 * Checks that different property names never share a handler object in the traced
 * operations. At least two distinct properties and valid handler identities are
 * needed. Different instances of the same implementation class are allowed.
 *
 * Success example: timeout uses IntegerHandler@a and top_k uses IntegerHandler@b.
 * Failure example: both timeout and top_k use IntegerHandler@a.
 * If only one property is observed, separation cannot be established: no credit.
 * Repeated use of one property's handler is checked separately.
 */
@util.annotations.Explanation("Checks that different properties use different handler objects; their implementation classes may be the same. Shared or missing identities fail; fewer than two properties means no credit.")
// Preserve this allocation when combined with fractional project-role scores.
@util.annotations.MaxValue(5)
public class PropertyHandlerInstancesSeparated extends AbstractPropertyConsistencyCheck {
    @Override protected CLIPropertyConsistencyChecks.Check rule() { return CLIPropertyConsistencyChecks.Check.DISTINCT_HANDLERS; }
}
