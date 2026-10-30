package gradingTools.comp690f26.assignment2.projectClassesSuite;
/**
 * Runs each A2 project-role check and examines failures for roles present in traces.
 * A traced dispatcher resolved in the project passes; a traced nonexistent dispatcher
 * fails this aggregate. Event checks separately resolve their participating classes. Untraced roles do not veto this aggregate:
 * required-role and event checks report missing evidence separately, as in A1.
 */
@util.annotations.Explanation("Checks all traced A2 roles for project classes; event checks resolve their own relevant classes.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.EVIDENCE_GATE)
public class A2TracedClassesExistInProject extends gradingTools.comp690f26.common.AbstractTracedClassesExistInProject {
    @Override protected Class<?>[] projectClassTests() { return A2ProjectClassesSuite.roleChecks(); }
}
