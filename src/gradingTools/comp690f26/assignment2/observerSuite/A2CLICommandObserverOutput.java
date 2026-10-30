package gradingTools.comp690f26.assignment2.observerSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.support.AbstractCLIArchitectureCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;


/**
 * Matches command notifications to observer receipts by command number and result identity, and checks display counts and captured output.
 * A matching notification and receipt pass; a changed result/chunk identity,
 * missing receipt or mismatched captured output fails. Trace-integrity prerequisites establish the required events. Dispatcher API and
 * field structure are independent assessments; their failure does not block delivery credit.
 * The semantic rule also rejects a processing object used as the regular observer.
 */
@util.annotations.Explanation("Matches command notifications to observer receipts by command number and result identity, and checks display counts and captured output.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.OBSERVER_DELIVERY)
public class A2CLICommandObserverOutput extends AbstractCLIArchitectureCheck {
    @Override protected CLIArchitectureChecks.Check architectureCheck() { return CLIArchitectureChecks.Check.COMMAND_OUTPUT; }
}
