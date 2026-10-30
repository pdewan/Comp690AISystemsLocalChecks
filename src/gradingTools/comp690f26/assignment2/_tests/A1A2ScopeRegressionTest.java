package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.*;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;

import static eduAI.trace.cli.MinimalCLITrace.*;

/** Exercises choices and omitted translation-event mutations, plus failed and partial A1
 * structural results. Unrelated defects preserve full semantic credit; consumed
 * bad evidence still fails. Uses copied fixtures and fresh grading executions. */
public final class A1A2ScopeRegressionTest {
    private static final double PARTIAL=0.5;
    private static final String EXTRA_MODEL="unused-extra-model";
    public static void main(String[] args)throws Exception {
        if(args.length!=2)throw new IllegalArgumentException("Supply A2 and A1 correct directories");
        Path source=Path.of(args[0]);F26Assignment2Suite.setTraceDirectory(args[0]);
        var p=F26Assignment2Suite.createTraceFilesProcessor(source);p.processTraceFiles();
        var records=new ArrayList<>(p.getTraceRecords().values());records.sort(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber));

        var policy=F26Assignment2Suite.propertyExpectations();var registry=F26Assignment2Suite.createPropertyCheckers();
        var rows=CLIPropertyTraceHistory.returns(records).operations().stream().map(CLIPropertyTraceHistory.Operation::fields).toList();
        String path=CLIPropertyResultFields.choicesFieldFromSelections(rows,new ArrayList<>());
        require(path!=null,"Correct choices field not found");
        var model=records.stream().filter(r->CLITraceEvidence.is(r,Event.PROPERTY_HANDLED)
            && eduAI.properties.AIPropertyNames.CURRENT_MODELS.equals(r.getTraceLine().getAuxiliaryData().get(NAME))).findFirst().orElseThrow();
        var extra=edit(records,model,d->d.put(path+".items."+CLIPropertyResultFields.choices(d,path).size()+".@value",EXTRA_MODEL));
        for(var kind:CLIPropertyChoicesChecks.Check.values())pass(CLIPropertyChoicesChecks.check(kind,extra,policy),"independent choices "+kind);
        fail(CLIPropertyConsistencyChecks.check(CLIPropertyConsistencyChecks.Check.ALTERNATIVES,extra,registry),"changed alternatives must fail consistency");
        // A1 keeps every architectural assessment and its points, but these
        // results no longer gate provider value/callback comparisons.
        F26Assignment1Suite.setTraceDirectory(args[1]);BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
        var structural=List.of(gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeServerHandleAdapters.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativePromptMessageAdapters.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMessageAdapters.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeResponseMetadataAdapters.class,
            gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeChunkCallbackAdapters.class);
        var parameters=gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters.class;
        var callback=gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaPartialResponseCallbackInvoked.class;
        var response=gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse.class;
        for(double fraction:List.of(0.0,PARTIAL)) {
            for(var type:structural) {
                var check=(grader.basics.testcase.PassFailJUnitTestCase)type.getDeclaredConstructor().newInstance();
                check.setLastResult(new TestCaseResult(fraction,"Independent structure result"));
            }
            full(response);full(parameters);full(callback);
            for(var type:structural)require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==fraction,"Structural score overwritten");
        }
        var integrity=JUnitTestsEnvironment.getPassFailJUnitTest(gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaResponseTypeIntegrity.class);
        integrity.setLastResult(new TestCaseResult(false,"Relevant response integrity failure"));reject(response);
        requestDomains(Path.of(args[1]));
        System.out.println("A1A2ScopeRegressionTest passed: choices and omitted translation events, structural failed/partial controls and relevant vetoes");System.exit(0);
    }
    /** Both translation events are required by the restored request contract;
     * omissions visibly fail that contract and block dependent request checks. */
    private static void requestDomains(Path source)throws Exception {
        var processor=F26Assignment1Suite.createTraceFilesProcessor(source);processor.processTraceFiles();

        var records=processor.getTraceRecords().values();
        Path fixture=Files.createTempDirectory(Path.of("build"),"request-domain-scope-");
        var originals=new LinkedHashMap<Path,List<String>>();
        for(var record:records) {
            Path file=Path.of(record.getTraceFileName());
            if(!originals.containsKey(file)) originals.put(file,Files.readAllLines(file));
        }
        for(var file:originals.keySet())Files.write(fixture.resolve(file.getFileName()),originals.get(file));
        F26Assignment1Suite.setTraceDirectory(fixture.toString());BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
        var parameters=gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters.class;
        var text=gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow.class;
        for(String event:List.of("message_translated","parameter_translated")) {
            for(var file:originals.keySet()) {
                var removed=records.stream().filter(r->r.getTraceFileName().equals(file.toString()) && r.getId().provider().equals(TraceProviderNames.OLLAMA)
                    && r.getTraceLine().getEventName().equals(event)).map(TraceRecord::getTraceFileLineNumber).collect(java.util.stream.Collectors.toSet());
                var lines=originals.get(file);var changed=new ArrayList<String>();
                for(int i=0;i<lines.size();i++)if(!removed.contains(i+1))changed.add(lines.get(i));
                Files.write(fixture.resolve(file.getFileName()),changed);
            }
            JUnitTestsEnvironment.clearCachedJUnitTestCases();
            // The restored request contract requires both preparation traces.
            // Either omission must visibly fail it and block its dependents.
            reject(gradingTools.comp690f26.assignment1.traceIntegritySuite.RequestsTraced.class);
            reject(parameters);
            reject(text);
        }
    }
    private static List<TraceRecord> edit(List<TraceRecord> records,TraceRecord selected,Consumer<Map<String,String>> mutation) {
        return records.stream().map(r->{if(r!=selected)return r;var t=r.getTraceLine();var d=new LinkedHashMap<>(t.getAuxiliaryData());mutation.accept(d);
            return new TraceRecord(r.getId(),new TraceLine(t.getLayerName(),t.getEventName(),t.getThreadPatternName(),t.getThreadName(),t.getDesignPatternName(),t.getSourceClassName(),d,""),r.getTraceFileName(),r.getTraceFileLineNumber());}).toList();
    }
    static void mutate(Path target,List<String> original,TraceRecord record,UnaryOperator<String> mutation)throws Exception {
        var lines=new ArrayList<>(original);int index=record.getTraceFileLineNumber()-1;lines.set(index,mutation.apply(lines.get(index)));
        require(!lines.equals(original),"Mutation did not change evidence");Files.write(target,lines);JUnitTestsEnvironment.clearCachedJUnitTestCases();
    }
    static TestCaseResult run(Class<?> type){var core=new org.junit.runner.JUnitCore();var listener=new AJUnitTestResult();core.addListener(listener);core.run(type);var result=listener.getTestCaseResult();JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);return result;}
    static void full(Class<?> type){var result=run(type);require(result.getPercentage()==1,type+": "+result.getNotes());}
    static void reject(Class<?> type){require(run(type).isFail(),"Relevant failure passed "+type);}
    private static void pass(TraceCheckResult result,String label){require(result.passed(),label+": "+result.level3ErrorMessages());}
    private static void fail(TraceCheckResult result,String label){require(!result.passed(),label);}
    static void require(boolean ok,String message){if(!ok)throw new AssertionError(message);}
}

