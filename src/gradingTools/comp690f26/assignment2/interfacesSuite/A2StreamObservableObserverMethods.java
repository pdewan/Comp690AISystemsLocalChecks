package gradingTools.comp690f26.assignment2.interfacesSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

/**
 * Requires only the resolved streaming observable and observer project classes.
 * Reflects on the resolved streaming observable and its traced observer classes.
 * A public instance method accepting an observer notification interface passes,
 * including inherited methods, parent notification interfaces and extra parameters.
 * Static/private methods, Object, concrete observer classes and unrelated interfaces
 * fail. Missing project-role evidence earns no credit through prerequisites.
 * No method name is required; this checks API shape, not registration side effects.
 */
@util.annotations.Explanation("Requires a public instance method on the streaming observable accepting its observer interface.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.METHOD_CONTRACT)
public class A2StreamObservableObserverMethods extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.STREAM_OBSERVER_METHODS; }
}
