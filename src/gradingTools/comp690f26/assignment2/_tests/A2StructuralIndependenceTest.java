package gradingTools.comp690f26.assignment2._tests;
import static eduAI.trace.cli.MinimalCLITrace.*;
import java.util.*;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.*;
import gradingTools.comp690f26.assignment2.interfacesSuite.*;
import gradingTools.comp690f26.assignment2.*;
import grader.basics.junit.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import eduAI.trace.TraceLine;
import eduAI.trace.processors.TraceRecord;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2CLISeparateFileExpanders;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2CLIStructureContract;
import java.nio.file.Path;



/** Structural evidence succeeds despite invalid displayed output. Missing
 * role evidence fails. Fresh grading executions retain full structural credit
 * when unrelated assessments are failed or partial, rather than using cached passes. */
public final class A2StructuralIndependenceTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 directory");
        A2ChecksConfiguration.setTraceDirectory(args[0]);
        var processor=A2ChecksConfiguration.createTraceFilesProcessor(Path.of(args[0]));
        processor.processTraceFiles();
        var records=processor.getTraceRecords().values().stream()
            .sorted(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber)).toList();
        var traces=records.stream().map(TraceRecord::getTraceLine).toList();
        require(MinimalCLIContractChecks.checkStructure(traces).isEmpty(),"Correct structural evidence failed");
        var cliEvents=Arrays.stream(Event.values()).map(Event::eventName).toList();
        var noSemantics=traces.stream().filter(t->cliEvents.contains(t.getEventName())).map(t->{
            var data=new LinkedHashMap<>(t.getAuxiliaryData());
            if(data.containsKey(OUTPUT)) data.put(OUTPUT,"deliberately invalid output syntax and content");
            return copy(t,data);
        }).toList();
        require(MinimalCLIContractChecks.checkStructure(noSemantics).isEmpty(),"Semantic values blocked structure");
        for(var event:List.of(Event.COMMAND_HANDLED,Event.PROPERTY_HANDLED,Event.HISTORY_WITH_PARAMETERS_UPDATED,
                Event.COMMAND_OUTPUT_NOTIFIED,Event.COMMAND_DISPLAYED,Event.STREAM_DISPLAYED,Event.FILE_EXPANDED))
            require(!MinimalCLIContractChecks.checkStructure(traces.stream()
                .filter(t->!t.getEventName().equals(event.eventName())).toList()).isEmpty(),"Missing structural evidence passed: "+event);
        var wrongRegistry=traces.stream().map(t->{
            var data=new LinkedHashMap<>(t.getAuxiliaryData());
            if(t.getEventName().equals(Event.COMMAND_HANDLED.eventName())) data.put(HANDLER,"mutation.Wrong@1");
            return copy(t,data);
        }).toList();
        require(MinimalCLIContractChecks.checkStructure(wrongRegistry).stream()
            .anyMatch(f->f.check().equals("registry-association")),"Wrong registry association passed");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        var root=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        var traceOnly=List.of(A2CLIStructureContract.class,A2CLISeparateFileExpanders.class);
        for(var type:traceOnly) require(JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(type).getLastResult().isPass(),"Baseline failed: "+type);
        for(var independent:List.of(gradingTools.comp690f26.common.io.CLIOuterOutputSyntax.class,
                gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIFileExpandedTraced.class,
                gradingTools.comp690f26.assignment2.projectClassesSuite.A2TracedClassesExistInProject.class))
            require(JUnitTestsEnvironment.getPassFailJUnitTest(independent)==null,"Unnecessary prerequisite executed: "+independent);
        var fields=List.of(A2DispatcherObserverTypes.class,A2StreamObservableObserverTypes.class);
        for(var type:fields) require(JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(type).getLastResult().isPass(),"Baseline fields failed: "+type);
        var independent=List.of(A2DispatcherObserverMethods.class,A2StreamObservableObserverMethods.class,
            gradingTools.comp690f26.common.io.CLIOuterOutputSyntax.class,
            gradingTools.comp690f26.assignment2.traceIntegritySuite.CLIFileExpandedTraced.class);
        for(var type:independent) {
            var check=JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(type);
            require(check.getLastResult().isPass(),"Independent baseline failed: "+type);
        }
        for(double fraction:List.of(0.0,0.5)) {
            for(var type:independent) JUnitTestsEnvironment.getPassFailJUnitTest(type)
                .setLastResult(new TestCaseResult(fraction,"Injected independent assessment result"));
            for(var type:List.of(A2CLIStructureContract.class,A2CLISeparateFileExpanders.class,
                    A2DispatcherObserverTypes.class,A2StreamObservableObserverTypes.class)) {
                var runner=new org.junit.runner.JUnitCore();
                var listener=new AJUnitTestResult();
                runner.addListener(listener);
                var execution=runner.run(type);
                require(execution.wasSuccessful(),"Independent result blocked fresh check: "+type+execution.getFailures());
                require(listener.getTestCaseResult().getPercentage()==1,
                    "Independent partial result scaled credit: "+type);
            }
        }
        System.out.println("A2StructuralIndependenceTest passed: invalid output, seven missing-event mutations, wrong registry, and fresh fail/partial-credit controls");
        System.exit(0);
    }
    private static TraceLine copy(TraceLine t,Map<String,String> data) {
        return new TraceLine(t.getLayerName(),t.getEventName(),t.getThreadPatternName(),t.getThreadName(),
            t.getDesignPatternName(),t.getSourceClassName(),data,"");
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
