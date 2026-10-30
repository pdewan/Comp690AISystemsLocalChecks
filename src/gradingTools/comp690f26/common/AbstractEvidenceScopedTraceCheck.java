package gradingTools.comp690f26.common;

import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.junit.JUnitTestsEnvironment;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;

/** Runs fixed evidence prerequisites first, then only correctness checks needed
 * by observed requests. Unused-provider checks neither run nor scale credit.
 * These are evidence dependencies, not an execution/coverage entry point. */
public abstract class AbstractEvidenceScopedTraceCheck extends AbstractTraceProcessorCheck {
    protected Class<?>[] evidencePrecedingTests(TraceFilesProcessor processor) { return new Class<?>[0]; }
    @Override protected void possiblyRunAndCheckPrecedingTests() {
        super.possiblyRunAndCheckPrecedingTests();
        var files=(AdaptiveTraceFilesProvided)getFirstPrecedingTestInstance();
        for(var type:PrecedingTestUtils.forProvidedFiles(evidencePrecedingTests(files.getTraceFilesProcessor()))) {
            if(getPrecedingTestInstances().stream().anyMatch(test->test.getClass()==type)) continue;
            var test=JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(type);
            org.junit.Assert.assertNotNull("Required evidence check unavailable: "+type.getSimpleName(),test);
            var result=test.getLastResult();
            org.junit.Assert.assertNotNull("Required evidence result unavailable: "+type.getSimpleName(),result);
            org.junit.Assert.assertFalse("Preceding test "+type.getSimpleName()+" failed: "+result.getNotes(),result.isFail());
            precedingTestInstances.add(test);
        }
    }
}
