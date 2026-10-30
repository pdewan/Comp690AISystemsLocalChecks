package gradingTools.comp690f26.assignment2.physicalStructureSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

@util.annotations.Explanation("Streaming observable instance fields reach observers through their notification interfaces.")
/**
 * Requires only resolved observable and observer classes; API-method scores are independent.
 * Uses resolved role classes and follows their declared instance-field types.
 * A path to the required interface passes; absent paths fail. Missing evidence earns no credit.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PHYSICAL_RELATIONSHIP)
public class A2StreamObservableObserverTypes extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.STREAM_OBSERVERS; }
}
