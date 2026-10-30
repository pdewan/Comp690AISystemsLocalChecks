package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.*;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.common.commands.*;
import java.nio.file.*;
import java.util.*;

import static eduAI.trace.cli.MinimalCLITrace.*;
/** Unrelated setter-history defects must preserve full credit; relevant mutations
 * must still fail. Tests pure helpers and fresh grading runs on a restricted
 * session, including unused-provider failed/partial controls. Missing handles
 * must fail both dependent and integrity checks, with identity diagnostics at
 * each affected request; unrelated failures cannot overwrite independent scores. */
public final class A2ScopedHelpersTest {
    private static final double PARTIAL=0.5;
    // Independent expectation of the diagnostic contract, not imported from production.
    private static final String MISSING_REQUEST_IDENTITY="No preceding provider/factory identity evidence for request at ";
    public static void main(String[] args)throws Exception {
        if(args.length!=1)throw new IllegalArgumentException("Supply correct A2 directory");
        var directory=Path.of(args[0]);F26Assignment2Suite.setTraceDirectory(args[0]);
        var p=F26Assignment2Suite.createTraceFilesProcessor(directory);p.processTraceFiles();
        var records=new ArrayList<>(p.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        var contracts=F26Assignment2Suite.createPropertyCheckers();
        var duplicate=new ArrayList<>(records);duplicate.add(records.stream().filter(r->CLITraceEvidence.is(r,Event.HISTORY_WITH_PARAMETERS_UPDATED)).findFirst().orElseThrow());
        for(var rule:List.of(CLIPropertyConsistencyChecks.Check.REGISTRATION,CLIPropertyConsistencyChecks.Check.HANDLER_REUSE,
                CLIPropertyConsistencyChecks.Check.DISTINCT_HANDLERS,CLIPropertyConsistencyChecks.Check.RESULT_TYPES,
                CLIPropertyConsistencyChecks.Check.ALTERNATIVES,CLIPropertyConsistencyChecks.Check.STORE_IDENTITY)) {
            pass(CLIPropertyConsistencyChecks.check(rule,records,contracts),"baseline "+rule);
            pass(CLIPropertyConsistencyChecks.check(rule,duplicate,contracts),"unrelated setter marker "+rule);
        }
        fail(CLIPropertyConsistencyChecks.check(CLIPropertyConsistencyChecks.Check.STORE,duplicate,contracts),"relevant malformed setter history");
        for(var rule:List.of(CLIArchitectureChecks.Check.COMMAND_OUTPUT,CLIArchitectureChecks.Check.STREAM_OUTPUT)) {
            pass(CLIArchitectureChecks.check(rule,records,io,directory),"baseline "+rule);
            var event=rule==CLIArchitectureChecks.Check.COMMAND_OUTPUT?Event.COMMAND_OUTPUT_NOTIFIED:Event.STREAM_OUTPUT_NOTIFIED;
            fail(CLIArchitectureChecks.check(rule,records.stream().filter(r->!CLITraceEvidence.is(r,event)).toList(),io,directory),"missing relevant notification "+rule);
        }
        var kinds=F26Assignment2Suite.attachmentKinds();
        var files=Files.createTempDirectory(Path.of("build"),"scoped-attachment-check-");
        Files.copy(directory.resolve(F26Assignment2Suite.REQUIRED_IMAGE_ATTACHMENT),files.resolve(F26Assignment2Suite.REQUIRED_IMAGE_ATTACHMENT));
        pass(CLIFileExpansionContentsChecks.check(CLIFileExpansionContentsChecks.Kind.IMAGE,records,io,files,kinds),"image without text file");
        pass(CLITraceExpectations.contentFrom(io,files,CLITraceExpectations.Check.IMAGES,kinds).check(records,null,CLITraceExpectations.Check.IMAGES),"expected image without text file");
        fail(CLIFileExpansionContentsChecks.check(CLIFileExpansionContentsChecks.Kind.TEXT,records,io,files,kinds),"required missing text");
        Files.copy(directory.resolve(F26Assignment2Suite.REQUIRED_TEXT_ATTACHMENT),files.resolve(F26Assignment2Suite.REQUIRED_TEXT_ATTACHMENT));
        Files.delete(files.resolve(F26Assignment2Suite.REQUIRED_IMAGE_ATTACHMENT));
        pass(CLIFileExpansionContentsChecks.check(CLIFileExpansionContentsChecks.Kind.TEXT,records,io,files,kinds),"text without image file");
        fail(CLIFileExpansionContentsChecks.check(CLIFileExpansionContentsChecks.Kind.IMAGE,records,io,files,kinds),"required missing image");
        Files.delete(files.resolve(F26Assignment2Suite.REQUIRED_TEXT_ATTACHMENT));
        for(var check:List.of(CLIInputOutputChecks.Check.MESSAGES,CLIInputOutputChecks.Check.SYSTEM_MESSAGES))
            require(!CLIInputOutputChecks.check(check,io,files,F26Assignment2Suite.attachmentKinds()).isEmpty(),"Missing consumed expanded text passed "+check);
        // A real prefix containing two nonstreaming Ollama prompts is sufficient
        // for prompt consistency, even though it does not satisfy the A2 script.
        var prompts=records.stream().filter(r->CLITraceEvidence.is(r,Event.COMMAND_HANDLED) && CLICommandNames.PROMPT.equals(CLITraceEvidence.commandName(r)))
            .sorted(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber)).toList();
        int boundary=prompts.get(1).getTraceFileLineNumber();
        var prefix=Files.createTempDirectory(Path.of("build"),"nonstream-provider-check-");
        Files.write(prefix.resolve(CLITraceInformation.TRACE_FILE_NAME),Files.readAllLines(directory.resolve(CLITraceInformation.TRACE_FILE_NAME)).subList(0,boundary));
        F26Assignment2Suite.setTraceDirectory(prefix.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);JUnitTestsEnvironment.clearCachedJUnitTestCases();
        full(AdaptivePromptCommandResultsMatch.class);
        var unused=List.of(gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse.class,
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiStreamingChunksAccumulatedCorrectly.class,
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaStreamingChunksAccumulatedCorrectly.class);
        for(var type:unused)require(JUnitTestsEnvironment.getPassFailJUnitTest(type)==null,"Unused prerequisite ran: "+type);
        for(double fraction:List.of(0.0,PARTIAL)) {
            for(var type:unused) {
                var test=(grader.basics.testcase.PassFailJUnitTestCase)type.getDeclaredConstructor().newInstance();
                test.setLastResult(new TestCaseResult(fraction,"Injected unused-provider/mode result"));
            }
            full(AdaptivePromptCommandResultsMatch.class);
            for(var type:unused) require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==fraction,
                "Unused prerequisite score was overwritten: "+type);
        }
        var required=JUnitTestsEnvironment.getPassFailJUnitTest(gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse.class);
        required.setLastResult(new TestCaseResult(false,"Injected relevant response failure"));
        require(run(AdaptivePromptCommandResultsMatch.class).isFail(),"Relevant response failure did not veto prompt consistency");
        required.setLastResult(new TestCaseResult(PARTIAL,"Injected relevant partial response result"));
        // Like the existing fixed prerequisites, partial success is not a veto.
        // Trace checks do not call the framework's optional score-scaling helper.
        full(AdaptivePromptCommandResultsMatch.class);
        require(required.getLastResult().getPercentage()==PARTIAL,"Required check's partial credit was overwritten");
        // Remove actual handle evidence as well as injecting a failed result:
        // restricting modes must never excuse a missing handle for a used mode.
        var noHandles=Files.createTempDirectory(Path.of("build"),"nonstream-missing-handles-");
        String handleEvent="{"+eduAI.trace.lib.LibTraceEvent.SERVER_HANDLE_FETCHED.name().toLowerCase(Locale.ROOT)+"}";
        var prefixLines=Files.readAllLines(prefix.resolve(CLITraceInformation.TRACE_FILE_NAME));
        var missingHandleLines=prefixLines.stream().filter(line->!line.contains(handleEvent)).toList();
        require(missingHandleLines.size()<prefixLines.size(),"Handle mutation removed no evidence");
        Files.write(noHandles.resolve(CLITraceInformation.TRACE_FILE_NAME),missingHandleLines);
        F26Assignment2Suite.setTraceDirectory(noHandles.toString());
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
        var missingHandles=run(AdaptivePromptCommandResultsMatch.class);
        require(missingHandles.isFail(),"Missing used-mode handle evidence passed");
        var integrity=JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.OllamaResponseTypeIntegrity.class).getLastResult();
        // Removing handles prevents their factories from identifying the consumed
        // requests. Require that precise cause at EVERY affected request, not just
        // an arbitrary failed prerequisite or the obsolete event-name diagnostic.
        require(integrity.isFail(),"Missing handles did not fail scoped response integrity");
        var handleCheck=JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.traceIntegritySuite.ServerHandlesFetchedTraced.class);
        require(handleCheck!=null && handleCheck.getLastResult()!=null && handleCheck.getLastResult().isFail(),
            "Missing handles did not produce a visible failed handle-integrity result");
        // JUnit now reports the failed prerequisite on dependent checks. Inspect
        // the provider evidence directly to retain the per-request diagnostic
        // assertion, rather than requiring an internally executed grade's notes.
        var missingProcessor=F26Assignment2Suite.createTraceFilesProcessor(noHandles);
        missingProcessor.processTraceFiles();
        var identityErrors=new java.util.ArrayList<String>();
        for(var request:missingProcessor.getTraceRecords().values())
            if(eduAI.trace.lib.LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT).equals(request.getTraceLine().getEventName()))
                identityErrors.addAll(gradingTools.comp690f26.common.TraceEvidenceView.select(missingProcessor,r->r==request)
                    .checkProviderNamesConsistent().level3ErrorMessages());
        String requestEvent="{"+eduAI.trace.lib.LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT)+"}";
        boolean sawRequest=false;
        for(int index=0;index<missingHandleLines.size();index++) if(missingHandleLines.get(index).contains(requestEvent)) {
            sawRequest=true;
            String location=noHandles.resolve(CLITraceInformation.TRACE_FILE_NAME)+":"+(index+1);
            require(identityErrors.stream().anyMatch(message->message.contains(MISSING_REQUEST_IDENTITY+location)),
                "Missing-handle identity diagnostic absent for request "+location+": "+identityErrors);
        }
        require(sawRequest,"Missing-handle control has no requests to validate");
        System.out.println("A2ScopedHelpersTest passed: setter, attachment and notification controls, relevant mutations and fresh unused-provider fail/partial controls");
        System.exit(0);
    }
    private static TestCaseResult run(Class<?> type) {
        var runner=new org.junit.runner.JUnitCore();var listener=new AJUnitTestResult();runner.addListener(listener);runner.run(type);
        var result=listener.getTestCaseResult();JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);return result;
    }
    private static void full(Class<?> type) {var result=run(type);require(result.getPercentage()==1,type+": "+result.getNotes());}
    private static void pass(TraceCheckResult r,String label){require(r.passed(),label+": "+r.level3ErrorMessages());}
    private static void fail(TraceCheckResult r,String label){require(!r.passed(),label+" unexpectedly passed");}
    private static void require(boolean ok,String message){if(!ok)throw new AssertionError(message);}
}
