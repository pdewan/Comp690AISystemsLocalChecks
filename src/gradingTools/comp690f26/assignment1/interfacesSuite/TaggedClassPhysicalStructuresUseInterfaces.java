package gradingTools.comp690f26.assignment1.interfacesSuite;

import gradingTools.comp690f26.assignment1.projectClassesSuite.Assignment1ProjectClassTests;
import gradingTools.comp690f26.common.AbstractTaggedClassPhysicalStructuresUseInterfaces;

@util.annotations.MaxValue(10)
@util.annotations.Explanation("Checks interface-typed fields of required classes and optional classes present in traces.")
public class TaggedClassPhysicalStructuresUseInterfaces extends AbstractTaggedClassPhysicalStructuresUseInterfaces {
    /** Missing optional merger roles do not veto or scale regular points. Required
     * roles still veto. The aggregate gate rejects any traced class absent from
     * the project; present optional classes undergo the same interface checks.
     * Thus no merger can pass, but a traced nonexistent merger cannot pass. */
    @Override protected boolean failedTestVetoes(Class type) {
        return !Assignment1ProjectClassTests.isOptional(type);
    }
    @Override protected boolean shouldScaleResult() { return false; }

    @Override protected Class[] precedingTests() { return Assignment1ProjectClassTests.interfacePrerequisites(); }
}
