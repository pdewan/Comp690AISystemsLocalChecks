package gradingTools.comp690f26.assignment2.semanticsSuite;

import gradingTools.comp690f26.assignment2.support.AbstractCLIContractCheck;


/**
 * Checks only the semantics domain of the full trace contract.
 * Results matching observed commands pass; wrong values, delivery, order, counts or history fail.
 * Unreadable or unavailable evidence earns no credit. Structural identity
 * rules and semantic value/sequence rules have disjoint classifications.
 */
@util.annotations.Explanation("Results matching observed commands pass; wrong values, delivery, order, counts or history fail. Unavailable evidence earns no credit.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.EVIDENCE_GATE)
public class A2CLISemanticsContract extends AbstractCLIContractCheck {
    @Override protected eduAI.trace.processors.cli.CLIContractDomains.Domain domain() {
        return eduAI.trace.processors.cli.CLIContractDomains.Domain.SEMANTICS;
    }
}
