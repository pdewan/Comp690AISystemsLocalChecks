package gradingTools.comp690f26.assignment2.interfacesSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

/**
 * Validates the regular observer's notification interface as part of API matching;
 * no streaming observer evidence is required.
 * Reflects on the resolved dispatcher and its traced observer classes.
 * An instance registration method earns full credit; constructor-only registration
 * earns half credit because observers must be supplied at construction time.
 * Parent notification interfaces and extra parameters are allowed; instance methods may be inherited.
 * Static/private methods, Object, concrete observer classes and unrelated interfaces
 * fail. Missing project-role evidence earns no credit through prerequisites.
 * Constructors must belong to this class; they are not inherited. A constructor
 * that stores the observer earns half API credit. A constructor that discards it
 * also earns half API credit but fails the separate storage check. Notification is
 * checked independently from traces. No method name is required.
 */
@util.annotations.Explanation("Full credit for an instance registration method accepting the observer interface; half credit for constructor-only registration, which limits observers to those known at construction time. Storage and notification are checked separately.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.METHOD_CONTRACT)
public class A2DispatcherObserverMethods extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.DISPATCHER_OBSERVER_METHODS; }
}
