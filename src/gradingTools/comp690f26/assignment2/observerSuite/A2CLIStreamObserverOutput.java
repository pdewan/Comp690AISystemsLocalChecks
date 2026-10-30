package gradingTools.comp690f26.assignment2.observerSuite;

import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.support.AbstractCLIArchitectureCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;


/**
 * Matches streaming notifications to observer receipts and A1 chunks by command number, model and chunk identity, and checks captured output.
 * A matching notification and receipt pass; a changed result/chunk identity,
 * missing receipt or mismatched captured output fails. Missing event evidence earns no credit through trace-integrity prerequisites.
 * Interface and field structure are independently scored and do not gate delivery. Observer role separation is
 * checked separately by ObserverRolesSeparated. Missing regular output does not block this check.
 * Nonstreaming request contents are not decoded for this comparison. Missing
 * streaming request/chunk evidence still fails.
 */
@util.annotations.Explanation("Matches streaming notifications, receipts and A1 chunks by command, model and identity, and checks captured output. Nonstreaming request contents do not gate credit; missing streaming evidence fails.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.OBSERVER_DELIVERY)
public class A2CLIStreamObserverOutput extends AbstractCLIArchitectureCheck {
    @Override protected CLIArchitectureChecks.Check architectureCheck() { return CLIArchitectureChecks.Check.STREAM_OUTPUT; }
}
