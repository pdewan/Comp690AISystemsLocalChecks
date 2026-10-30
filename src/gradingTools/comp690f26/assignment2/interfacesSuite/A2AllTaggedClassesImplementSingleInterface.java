package gradingTools.comp690f26.assignment2.interfacesSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

/**
 * Uses every A2 role class resolved by the project checks. Exactly one most-specific
 * interface passes, including an interface inherited from a superclass. No interface
 * or two unrelated interfaces fail. The A2 project aggregate must pass first;
 * a traced missing class blocks credit. Common-interface checks remain separate.
 */
@util.annotations.Explanation("After the A2 project aggregate passes, requires exactly one most-specific interface for every A2 tagged class.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.SINGLE_INTERFACE)
public class A2AllTaggedClassesImplementSingleInterface extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.SINGLE_INTERFACE; }
}
