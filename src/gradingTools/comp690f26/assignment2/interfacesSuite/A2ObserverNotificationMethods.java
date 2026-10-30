package gradingTools.comp690f26.assignment2.interfacesSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

/**
 * Uses command and streaming observer classes resolved by the project checks.
 * An inherited interface void method with a data parameter passes. Marker interfaces,
 * static-only methods, no-argument methods and non-void methods fail. This checks
 * method shape, not delivery or displayed content. Missing evidence earns no credit.
 */
@util.annotations.Explanation("Both observer interfaces must offer an instance void method accepting notification data.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.METHOD_CONTRACT)
public class A2ObserverNotificationMethods extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.OBSERVER_NOTIFICATION_METHODS; }
}
