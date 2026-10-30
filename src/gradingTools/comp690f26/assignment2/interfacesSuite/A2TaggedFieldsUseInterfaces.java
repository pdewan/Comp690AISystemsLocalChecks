package gradingTools.comp690f26.assignment2.interfacesSuite;

import gradingTools.comp690f26.assignment2.projectClassesSuite.A2ProjectClassesSuite;

/**
 * Follows declared instance-field types of the resolved A2 role classes.
 * A field declared using a collaborator interface passes; a concrete tagged
 * collaborator field fails, including through nested project field types.
 * The project aggregate must pass before examining fields: a traced missing
 * class blocks credit, while resolved classes with interface fields can pass.
 */
@util.annotations.Explanation("After the A2 project aggregate passes, checks that CLI fields use interfaces instead of concrete tagged collaborator classes.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.INTERFACE_FIELDS)
public class A2TaggedFieldsUseInterfaces extends gradingTools.comp690f26.common.AbstractTaggedClassPhysicalStructuresUseInterfaces {
    @Override protected Class[] precedingTests() {
        return A2ProjectClassesSuite.interfacePrerequisites();
    }
}
