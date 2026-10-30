package gradingTools.comp690f26.common.properties;

import eduAI.trace.processors.*;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.files.*;
import java.util.List;

/**
 * Runs semantic property consistency and required-choice checks; registration,
 * handler reuse and handler separation are assessed independently. These semantic
 * checks and the I/O-file prerequisite run before checking the suite's configured property input sequence. Compare operation
 * count, order, property names, get/set kinds, any required values and required
 * changes. Then match each actual input to its traced command number, property,
 * operation kind and value. The consistency checks do not depend on this test.
 *
 * Suite-configured request and snapshot checks also run before input validation.
 * Success example: within a session satisfying the other configured operations
 * and prerequisites, the timeout portion requires set, get, then change. Values
 * 120, get, 240 with matching traces satisfy that portion; 300, get, 600 also
 * satisfy it when no exact values were configured.
 * Failure examples: a required getter is omitted, the order is wrong, a required
 * change repeats the old value, or the user enters 120 while consistent setter
 * and getter traces both claim 240. A failed consistency prerequisite blocks
 * this check. Expectations come from suite configuration, not this test class.
 */
@util.annotations.Explanation("Also runs suite-configured request-settings and snapshot-preservation checks. First runs the property consistency and required-choice checks. Then compares actual inputs with the suite-configured operation sequence, required values and changes, and matches inputs to traced operations and values. Wrong inputs or trace/input mismatches fail; failed prerequisites block this check.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.REQUIRED_INPUT)
public class AdaptiveExpectedPropertyInputs extends AbstractTraceProcessorCheck {
    @Override protected Class<?>[] additionalPrecedingTests() {
        return gradingTools.comp690f26.common.PrecedingTestUtils.add(new Class<?>[]{CLIOuterOutputSyntax.class,PropertyResultTypesConsistent.class,PropertyAlternativesConsistent.class,BooleanPropertyAlternatives.class,RequiredModelAlternatives.class,PropertyGettersMatchSetters.class,PropertyStoreConsistent.class}, FilesProvidedSupport.configuration().checks().propertyInputPrerequisites().toArray(Class<?>[]::new));
    }
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        var policy=FilesProvidedSupport.configuration().checks().propertyExpectations()
            .orElseThrow(()->new IllegalStateException("Expected property inputs must be configured by the suite"));
        for(var prerequisite:getPrecedingTestInstances())
            if(prerequisite instanceof CLIOuterOutputSyntax input && input.getInteractions().isPresent())
                return policy.check(processor.getTraceRecords().values(),input.getInteractions().orElseThrow());
        return new TraceCheckResult(List.of(),List.of(),List.of("CLI inputs unavailable"));
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) { return String.join("\n",result.level3ErrorMessages()); }
}
