package gradingTools.comp690f26.assignment2.observerSuite;

import gradingTools.comp690f26.assignment2.support.AbstractObserverOutputCheck;

/**
 * Checks notification-to-receipt identity, ordering and completeness using traces only.
 * Matching deliveries pass even if the I/O file is missing, malformed or from another run. A
 * missing notification, changed received identity or missing receipt fails. No relevant
 * delivery evidence earns no credit. Result contents and output-file agreement are
 * separately scored.
 */
@util.annotations.Explanation("Checks traced notification delivery by identity, order and completeness without depending on the I/O file. Missing or inconsistent delivery evidence fails.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.OBSERVER_DELIVERY)
public class A2CLIStreamObserverOutput extends AbstractObserverOutputCheck {
    @Override protected boolean streaming() { return true; }
    @Override protected boolean agreement() { return false; }
}
