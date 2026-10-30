package gradingTools.comp690f26.assignment2.commonInterfacesSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

@util.annotations.Explanation("Property handlers share an interface defining a result-returning operation.")
/**
 * Uses resolved role classes and their interfaces. Compatible shared operation
 * contracts pass; absent or incompatible contracts fail. Missing evidence earns no credit.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMON_INTERFACE)
public class A2PropertyHandlersCommonInterface extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.PROPERTY_INTERFACE; }
}
