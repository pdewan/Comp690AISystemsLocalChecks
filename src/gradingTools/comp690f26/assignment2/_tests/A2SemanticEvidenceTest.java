package gradingTools.comp690f26.assignment2._tests;

import eduAI.properties.AIPropertyNames;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.ObserverRolesSeparated;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.*;
import java.util.*;


import static eduAI.trace.cli.MinimalCLITrace.*;

/** Missing-evidence mutations must fail even when no contradictory value exists.
 * Also proves regular output does not require streaming evidence or prerequisites. */
public final class A2SemanticEvidenceTest {
    private static int mutations;
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 fixture directory");
        Path directory=Path.of(args[0]); F26Assignment2Suite.configureProperties();
        var processor=F26Assignment2Suite.createTraceFilesProcessor(directory); processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME))
            .interaction().orElseThrow();
        var policy=F26Assignment2Suite.propertyExpectations();
        var contracts=CLIPropertyCheckerRegistry.fromDescriptors(policy.descriptors());
        for(var rule:List.of(CLIPropertyConsistencyChecks.Check.RESULT_TYPES,CLIPropertyConsistencyChecks.Check.ALTERNATIVES,
                CLIPropertyConsistencyChecks.Check.STORE,CLIPropertyConsistencyChecks.Check.GETTERS)) {
            pass(CLIPropertyConsistencyChecks.check(rule,records,contracts),"correct "+rule);
            reject(CLIPropertyConsistencyChecks.check(rule,List.of(),contracts),"absent "+rule);
        }
        for(var rule:CLIPropertyChoicesChecks.Check.values()) {
            pass(CLIPropertyChoicesChecks.check(rule,records,policy),"correct "+rule);
            reject(CLIPropertyChoicesChecks.check(rule,List.of(),policy),"absent "+rule);
        }
        var onlyGetters=records.stream().filter(r->!CLITraceEvidence.is(r,Event.HISTORY_WITH_PARAMETERS_UPDATED)).toList();
        insufficient(CLIPropertyConsistencyChecks.check(CLIPropertyConsistencyChecks.Check.STORE,onlyGetters,contracts),"store without setters");
        insufficient(CLIPropertyConsistencyChecks.check(CLIPropertyConsistencyChecks.Check.GETTERS,onlyGetters,contracts),"getters without setters");
        var config=FilesProvidedSupport.configuration().checks().commandChecks().orElseThrow();
        var registry=config.traceRules().get();
        registry.register(CLICommandNames.MESSAGES,new CLIMessagesCommandResultChecker(io,directory,F26Assignment2Suite.attachmentKinds()));
        for(String command:List.of(CLICommandNames.PROPERTY,CLICommandNames.PROMPT)) {
            pass(CLICommandResultChecks.checkTraces(records,config.traceRules().get(),command),"correct "+command);
            reject(CLICommandResultChecks.checkTraces(List.of(),config.traceRules().get(),command),"absent "+command);
        }
        for(String command:List.of(CLICommandNames.TRANSCRIPT,CLICommandNames.MESSAGES)) {
            pass(CLICommandResultChecks.check(records,io,registry,command),"correct "+command);
            reject(CLICommandResultChecks.check(List.of(),new CLIMixedIO(List.of()),registry,command),"absent "+command);
        }
        for(var kind:CLIFileExpansionContentsChecks.Kind.values()) {
            pass(CLIFileExpansionContentsChecks.check(kind,records,io,directory,F26Assignment2Suite.attachmentKinds()),"correct "+kind);
            reject(CLIFileExpansionContentsChecks.check(kind,records,new CLIMixedIO(List.of()),directory,F26Assignment2Suite.attachmentKinds()),"absent "+kind);
        }
        for(var rule:List.of(CLIArchitectureChecks.Check.HISTORY,CLIArchitectureChecks.Check.COMMAND_OUTPUT,CLIArchitectureChecks.Check.STREAM_OUTPUT)) {
            pass(CLIArchitectureChecks.check(rule,records,io,directory),"correct "+rule);
            reject(CLIArchitectureChecks.check(rule,List.of(),io,directory),"absent "+rule);
        }
        var expectations=CLITraceExpectations.from(io,directory);
        pass(expectations.check(records,null,CLITraceExpectations.Check.SETTINGS),"correct settings");
        reject(expectations.check(List.of(),null,CLITraceExpectations.Check.SETTINGS),"absent requests");

        // Remove all requests from the snapshot comparison while keeping setter checkpoints.
        var noRequests=records.stream().filter(r->!List.of("request_sent","partial_response_callback_invoked",
            "complete_response_callback_invoked").contains(r.getTraceLine().getEventName())).toList();
        var noPrompts=new CLIMixedIO(io.entries().stream().filter(e->e.input().kind()!=CLIInteraction.Kind.PROMPT).toList());
        insufficient(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.HISTORY,noRequests,noPrompts,directory),"no prior requests to preserve");
        var firstPrompt=io.entries().stream().filter(e->e.input().kind()==CLIInteraction.Kind.PROMPT).findFirst().orElseThrow();
        int firstReturnLine=records.stream().filter(r->CLITraceEvidence.is(r,Event.COMMAND_HANDLED)
            && CLITraceEvidence.number(r.getTraceLine().getAuxiliaryData())==firstPrompt.number())
            .findFirst().orElseThrow().getTraceFileLineNumber();
        var initialRecords=records.stream().filter(r->r.getTraceFileLineNumber()<=firstReturnLine).toList();
        var initialIO=new CLIMixedIO(io.entries().stream().filter(e->e.number()<=firstPrompt.number()).toList());
        insufficient(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.HISTORY,initialRecords,initialIO,directory),"setters only before first request");
        var noStreaming=records.stream().filter(r->{ String event=r.getTraceLine().getEventName();
            return !event.equals("partial_response_callback_invoked") && !event.equals("complete_response_callback_invoked")
                && !CLITraceEvidence.is(r,Event.STREAM_DISPLAYED) && !CLITraceEvidence.is(r,Event.STREAM_OUTPUT_NOTIFIED);
        }).toList();
        insufficient(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.STREAM_OUTPUT,noStreaming,io,directory),"no streaming comparisons");
        pass(CLIArchitectureChecks.check(CLIArchitectureChecks.Check.COMMAND_OUTPUT,noStreaming,io,directory),"regular output without streaming");
        assertRegularPrerequisites(A2CLICommandObserverOutput.class,new HashSet<>());

        for(String variant:List.of("ordinary-only","system-only")) {
            var changed=mutateInputs(io,variant);
            require(!CLISemanticEvidence.messages(changed).isEmpty(),"Messages evidence mutation survived: "+variant); mutations++;
        }
        for(String variant:List.of("unchanged-settings","no-numeric-settings","one-model","one-mode")) {
            var changed=mutateInputs(io,variant);
            insufficient(CLITraceExpectations.from(changed,directory).check(records,null,CLITraceExpectations.Check.SETTINGS),variant);
        }
        var transcript=io.entries().stream().filter(e->CLICommandNames.TRANSCRIPT.equals(e.input().commandName())).findFirst().orElseThrow();
        var earlier=io.entries().stream().filter(e->e.number()<transcript.number() && e.input().kind()!=CLIInteraction.Kind.PROMPT).toList();
        var context=new CLICommandResultContext(transcript,earlier,new CLIResultEvidence(""),List.of(),Map.of(),Map.of(),List.of());
        require(new CLITranscriptCommandResultChecker().check(context).stream().anyMatch(s->s.startsWith("Insufficient evidence:")),"Transcript without responses survived"); mutations++;
        System.out.println("A2SemanticEvidenceTest passed: "+mutations+" missing-evidence mutations; all 16 correct checks; independent regular output");
        System.exit(0);
    }
    private static CLIMixedIO mutateInputs(CLIMixedIO io,String variant) {
        var first=io.entries().stream().filter(e->e.input().kind()==CLIInteraction.Kind.PROMPT).findFirst().orElseThrow().input().properties();
        var entries=new ArrayList<CLIMixedIO.Entry>();
        for(var entry:io.entries()) {
            var input=entry.input();
            if(CLICommandNames.MESSAGES.equals(input.commandName()) &&
                (variant.equals("ordinary-only") && !input.argument().isBlank() || variant.equals("system-only") && input.argument().isBlank())) continue;
            var properties=new LinkedHashMap<>(input.properties());
            if(variant.equals("unchanged-settings")) for(String name:CLISemanticEvidence.NUMERIC_PROPERTIES) {
                properties.remove(name); if(first.containsKey(name)) properties.put(name,first.get(name));
            }
            if(variant.equals("no-numeric-settings")) CLISemanticEvidence.NUMERIC_PROPERTIES.forEach(properties::remove);
            if(variant.equals("one-model")) properties.put(AIPropertyNames.CURRENT_MODELS,first.get(AIPropertyNames.CURRENT_MODELS));
            if(variant.equals("one-mode")) { properties.put(AIPropertyNames.STREAM,false); properties.remove(AIPropertyNames.STREAMING_ALIAS); }
            var changed=new CLIInteraction.Command(input.inputOrdinal(),input.lineNumber(),input.rawInput(),input.kind(),input.commandName(),input.argument(),input.propertyName(),properties);
            entries.add(new CLIMixedIO.Entry(entry.number(),changed,variant.equals("no-attachments")?List.of():entry.attachments(),entry.output(),entry.chunks()));
        }
        return new CLIMixedIO(entries);
    }
    private static void assertRegularPrerequisites(Class<?> type,Set<Class<?>> visited) throws Exception {
        if(!visited.add(type)) return;
        // This aggregate validates classes for roles that actually appear in traces.
        // Absent roles do not veto it; its listed project tests are not required
        // streaming behavior/evidence. Do not mistake this conditional lookup for
        // a requirement to exercise streaming in a regular-output session.
        if(gradingTools.comp690f26.common.AbstractTracedClassesExistInProject.class.isAssignableFrom(type)) return;
        require(!type.getSimpleName().contains("Stream") && type!=ObserverRolesSeparated.class
            && type!=gradingTools.comp690f26.assignment2.interfacesSuite.A2ObserverNotificationMethods.class,
            "Regular output depends on streaming: "+type);
        var method=grader.basics.testcase.PassFailJUnitTestCase.class.getDeclaredMethod("precedingTests"); method.setAccessible(true);
        var dependencies=(Class<?>[])method.invoke(type.getConstructor().newInstance());
        if(dependencies!=null) for(var dependency:dependencies) assertRegularPrerequisites(dependency,visited);
    }
    private static void pass(TraceCheckResult result,String label) { require(result.passed(),label+": "+result.level3ErrorMessages()); }
    private static void reject(TraceCheckResult result,String label) {
        require(!result.passed(),label+" earned credit"); mutations++;
        System.out.println("Rejected missing-evidence mutation: "+label);
    }
    private static void insufficient(TraceCheckResult result,String label) {
        reject(result,label); require(result.level3ErrorMessages().stream().anyMatch(s->s.toLowerCase().contains("insufficient evidence")),label+": "+result.level3ErrorMessages());
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
