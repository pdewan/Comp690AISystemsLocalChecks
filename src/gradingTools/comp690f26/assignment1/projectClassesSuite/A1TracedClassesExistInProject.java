package gradingTools.comp690f26.assignment1.projectClassesSuite;
/**
 * Uses the released A1 project-role prerequisites. A traced class that resolves
 * passes; a traced but missing class fails. Untraced roles are checked separately
 * and do not veto this gate. The shared implementation preserves that behavior.
 */
@util.annotations.Explanation("Checks project classes for all traced roles; event checks resolve their own participating classes.")
public class A1TracedClassesExistInProject extends gradingTools.comp690f26.common.AbstractTracedClassesExistInProject {
    @Override protected Class<?>[] projectClassTests() { return Assignment1ProjectClassTests.all(); }
}
