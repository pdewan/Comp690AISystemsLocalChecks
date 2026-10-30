package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.ObserverRolesSeparated;
import gradingTools.comp690f26.assignment2.parameterHistorySuite.A2CLIParameterHistorySnapshotsPreserved;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveExpectedCommandInputs;
import gradingTools.comp690f26.common.properties.AdaptiveExpectedPropertyInputs;
import java.nio.file.*;
import java.util.*;


import static eduAI.trace.cli.MinimalCLITrace.*;

/** Deletes unrelated evidence and verifies independent checks still pass;
 * deletes required evidence and verifies those same checks fail. Finally selects
 * expected inputs first, proving real grading prerequisite execution. */
public final class A2PrerequisiteIsolationTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 directory");
        Path directory=Path.of(args[0]); F26Assignment2Suite.setTraceDirectory(args[0]);
        var processor=F26Assignment2Suite.createTraceFilesProcessor(directory);processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        Path missing=directory.resolve("missing-attachments-for-isolation-test");
        require(!Files.exists(missing),"Isolation directory must not exist");
        for(var rule:List.of(CLIArchitectureChecks.Check.COMMAND_OUTPUT,CLIArchitectureChecks.Check.STREAM_OUTPUT,
                CLIArchitectureChecks.Check.HISTORY,CLIArchitectureChecks.Check.FILE_SEPARATION)) {
            var result=CLIArchitectureChecks.check(rule,records,io,missing);
            require(result.passed(),rule+" depends on unrelated file contents: "+result.level3ErrorMessages());
        }
        var noRegular=records.stream().filter(r->!CLITraceEvidence.is(r,Event.COMMAND_DISPLAYED)
            && !CLITraceEvidence.is(r,Event.COMMAND_OUTPUT_NOTIFIED)).toList();
        require(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.STREAM_OUTPUT,noRegular,io,missing).passed(),"Stream blocked by regular output");
        var noModel=records.stream().filter(r->!List.of("request_sent","partial_response_callback_invoked","complete_response_callback_invoked").contains(r.getTraceLine().getEventName())).toList();
        require(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.COMMAND_OUTPUT,noModel,io,missing).passed(),"Regular output blocked by model evidence");
        for(String mutation:List.of("shared-expander","changed-expander","one-kind")) {
            var changed=new ArrayList<TraceRecord>();
            for(var record:records) {
                if(!CLITraceEvidence.is(record,Event.FILE_EXPANDED)) { changed.add(record);continue; }
                var line=record.getTraceLine();var fields=new LinkedHashMap<>(line.getAuxiliaryData());
                if(mutation.equals("one-kind") && MinimalCLIContractChecks.values(fields,EXPANDED_DATA).stream()
                        .anyMatch(value->eduAI.trace.ImageByteSummary.parseFirstNonempty(value)!=null)) continue;
                if(mutation.equals("shared-expander")) fields.put(EXPANDER,"mutation.Shared@1");
                if(mutation.equals("changed-expander")) fields.put(EXPANDER,"mutation.Changed@"+record.getTraceFileLineNumber());
                changed.add(new TraceRecord(record.getId(),new eduAI.trace.TraceLine(line.getLayerName(),line.getEventName(),
                    line.getThreadPatternName(),line.getThreadName(),line.getDesignPatternName(),line.getSourceClassName(),fields,""),
                    record.getTraceFileName(),record.getTraceFileLineNumber()));
                // Exercise reuse even if this session attaches only one file per kind.
                if(mutation.equals("changed-expander")) changed.add(record);
            }
            require(!CLIArchitectureChecks.check(CLIArchitectureChecks.Check.FILE_SEPARATION,changed,io,missing).passed(),"Expander mutation survived: "+mutation);
        }
        for(var event:List.of(Event.COMMAND_DISPLAYED,Event.STREAM_DISPLAYED,Event.HISTORY_WITH_PARAMETERS_UPDATED,Event.FILE_EXPANDED)) {
            var rule=switch(event) {
                case COMMAND_DISPLAYED -> CLIArchitectureChecks.Check.COMMAND_OUTPUT;
                case STREAM_DISPLAYED -> CLIArchitectureChecks.Check.STREAM_OUTPUT;
                case HISTORY_WITH_PARAMETERS_UPDATED -> CLIArchitectureChecks.Check.HISTORY;
                case FILE_EXPANDED -> CLIArchitectureChecks.Check.FILE_SEPARATION;
                default -> throw new AssertionError(event);
            };
            require(!CLIArchitectureChecks.check(rule,records.stream().filter(r->!CLITraceEvidence.is(r,event)).toList(),io,missing).passed(),"Missing required evidence passed: "+event);
        }
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        verifyAcyclic(AdaptiveExpectedPropertyInputs.class,new HashSet<>(),new HashSet<>());
        verifyAcyclic(AdaptiveExpectedCommandInputs.class,new HashSet<>(),new HashSet<>());
        var root=BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        root.findTest(gradingTools.comp690f26.combined.A1A2ExpectedContentSuite.class).test();
        for(var type:List.of(AdaptiveExpectedPropertyInputs.class,AdaptiveExpectedCommandInputs.class))
            require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().isPass(),"Expected-input entry point failed: "+type);
        for(var type:List.of(A2CLIRequestSettingsMatch.class,A2CLIParameterHistorySnapshotsPreserved.class,
                A2CLITextFileExpansionContentsMatch.class,A2CLIImageFileExpansionContentsMatch.class,
                A2CLICommandObserverOutput.class,A2CLIStreamObserverOutput.class))
            require(root.findTest(type).getFractionComplete()==1,"Expected inputs did not trigger "+type);
        // Starting with expected inputs executes its whole-session semantic checks.
        // Required integrity and translation checks receive visible grades.
        for(var suite:List.of(
                gradingTools.comp690f26.assignment1.requestTranslationSuite.RequestTranslationSuite.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite.class,
                gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveExpectedRequestContentSuite.class))
            for(var type:suite.getAnnotation(org.junit.runners.Suite.SuiteClasses.class).value())
                if(!type.isAnnotationPresent(util.annotations.IsExtra.class) || !type.getAnnotation(util.annotations.IsExtra.class).value())
                    require(root.findTest(type).getFractionComplete()==1,"Expected inputs did not execute A1 evidence chain: "+type);
        for(var type:List.of(
                gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse.class,
                gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse.class))
            require(root.findTest(type).getFractionComplete()==1,"Expected inputs missed response translation: "+type);
        // Independent regular and extra-credit assessments must stay unrun.
        for(var type:List.of(
                gradingTools.comp690f26.common.properties.PropertyHandlersRegistered.class,
                gradingTools.comp690f26.common.properties.PropertyHandlerInstancesReused.class,
                gradingTools.comp690f26.common.properties.PropertyHandlerInstancesSeparated.class,
                gradingTools.comp690f26.common.commands.CommandHandlersRegistered.class,
                gradingTools.comp690f26.common.commands.CommandHandlerInstancesReused.class,
                ObserverRolesSeparated.class,
                gradingTools.comp690f26.assignment2.interfacesSuite.A2ObserverNotificationMethods.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2DispatcherObserverTypes.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2StreamObservableObserverTypes.class,
                gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2CommandRegistryHandlerTypes.class,
                gradingTools.comp690f26.assignment2.physicalStructureSuite.A2PropertyRegistryHandlerTypes.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse.class,
                gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.OllamaCompleteResponseCallbackInvokedWithMergedResponse.class))
            require(JUnitTestsEnvironment.getPassFailJUnitTest(type)==null,"Unrelated check was forced to execute: "+type);
        // Seed independent fail/partial results and reselect the inputs. Their
        // original full credit must survive; independent results must not be rerun.
        var optionalGemini=new gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.GeminiCompleteResponseCallbackInvokedWithMergedResponse();
        optionalGemini.setLastResult(new TestCaseResult(false,"Injected optional final-callback failure"));
        var optionalOllama=new gradingTools.comp690f26.assignment1.streamingBehaviorSuite.extra.OllamaCompleteResponseCallbackInvokedWithMergedResponse();
        optionalOllama.setLastResult(new TestCaseResult(0.5,"Injected optional final-callback partial credit"));
        var registration=new gradingTools.comp690f26.common.commands.CommandHandlersRegistered();
        registration.setLastResult(new TestCaseResult(false,"Injected registration design failure"));
        var properties=new gradingTools.comp690f26.common.properties.PropertyHandlerInstancesSeparated();
        properties.setLastResult(new TestCaseResult(0.5,"Injected property separation partial credit"));
        var observers=new ObserverRolesSeparated();
        observers.setLastResult(new TestCaseResult(false,"Injected observer role failure"));
        var interfaces=new gradingTools.comp690f26.assignment1.commonInterfacesSuite.NativeParameterAdapters();
        interfaces.setLastResult(new TestCaseResult(false,"Injected common-interface failure"));
        var failed=new gradingTools.comp690f26.assignment2.physicalStructureSuite.A2CommandRegistryHandlerTypes();
        failed.setLastResult(new TestCaseResult(false,"Injected independent failure"));
        var partial=new gradingTools.comp690f26.assignment2.physicalStructureSuite.A2PropertyRegistryHandlerTypes();
        partial.setLastResult(new TestCaseResult(0.5,"Injected independent partial credit"));
        for(var type:List.of(AdaptiveExpectedPropertyInputs.class,AdaptiveExpectedCommandInputs.class)) {
            require(freshRun(type).isPass(),"Independent result blocked input credit: "+type);
            require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==1,"Independent partial result scaled input credit");
        }
        require(optionalGemini.getLastResult().isFail() && optionalOllama.getLastResult().getPercentage()==0.5,
            "Optional final-callback results overwritten");
        require(failed.getLastResult().isFail() && partial.getLastResult().getPercentage()==0.5,
            "Independent results overwritten");
        // A failed regular accumulation prerequisite blocks only consumers of
        // its completed answer. Property and I/O-only credit remains available.
        var accumulation=JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiStreamingChunksAccumulatedCorrectly.class);
        var accumulationResult=accumulation.getLastResult();
        accumulation.setLastResult(new TestCaseResult(false,"Injected regular accumulation failure"));
        require(freshRun(gradingTools.comp690f26.common.commands.AdaptivePromptCommandResultsMatch.class).isFail(),
            "Prompt results ignored failed accumulation");
        require(freshRun(AdaptiveExpectedPropertyInputs.class).isPass(),"Accumulation failure blocked unrelated property credit");
        require(freshRun(gradingTools.comp690f26.common.io.AdaptiveStreamingOutputMatchesSetting.class).isPass(),
            "Trace accumulation failure blocked I/O-only credit");
        accumulation.setLastResult(accumulationResult);
        var retention=JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiMergedStreamingResponseInNextContextWindow.class);
        var retentionResult=retention.getLastResult();
        retention.setLastResult(new TestCaseResult(false,"Injected regular retention failure"));
        require(freshRun(gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedPromptsInProviderRequests.class).isFail(),
            "Request content ignored failed streaming retention");
        require(freshRun(gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveOllamaExpectedPromptsInProviderRequests.class).isPass(),
            "Gemini retention failure blocked Ollama content credit");
        retention.setLastResult(retentionResult);
        System.out.println("A2PrerequisiteIsolationTest passed: six unrelated-evidence controls, eight mutations, genuine chains, unrun independent checks and independent fail/partial-credit controls");
        System.exit(0);
    }
    /** GradableJUnitTest.test() returns cached results. JUnitCore creates a fresh
     * consumer while retaining the seeded prerequisite results. Publish its result
     * just as the grading listener does, so later consumers see this execution. */
    private static TestCaseResult freshRun(Class<?> type) {
        var execution=org.junit.runner.JUnitCore.runClasses(type);
        var result=new TestCaseResult(execution.wasSuccessful(),execution.getFailures().toString());
        JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);
        return result;
    }
    private static void verifyAcyclic(Class<?> type,Set<Class<?>> active,Set<Class<?>> complete) throws Exception {
        if(complete.contains(type)) return;
        require(active.add(type),"Prerequisite cycle: "+type);
        var method=grader.basics.testcase.PassFailJUnitTestCase.class.getDeclaredMethod("precedingTests");
        method.setAccessible(true);
        for(var dependency:(Class<?>[])method.invoke(type.getDeclaredConstructor().newInstance()))
            verifyAcyclic(dependency,active,complete);
        active.remove(type);complete.add(type);
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
