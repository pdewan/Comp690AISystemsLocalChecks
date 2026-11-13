package gradingTools.comp690f26.assignment2.observerSuite;

import gradingTools.comp690f26.assignment2.support.AbstractObserverOutputCheck;

/**
 * Compares traced displayed output with the same-run I/O file. Matching captures pass;
 * changed or missing captured output fails. Streaming also checks callback identity, order
 * and text. Missing display or I/O evidence earns no credit. Notification correctness is
 * separately scored and is not a prerequisite.
 */
@util.annotations.Explanation("Checks traced display against captured I/O; streaming also checks callback identity, order and text. Notification delivery is independently scored.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.OBSERVER_OUTPUT_AGREEMENT)
public class A2CLICommandOutputAgreement extends AbstractObserverOutputCheck {
    @Override protected boolean streaming() { return false; }
    @Override protected boolean agreement() { return true; }
}
