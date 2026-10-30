package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.TraceLine;
import eduAI.trace.cli.TraceDataPaths;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.*;
import java.util.*;

import static eduAI.trace.cli.MinimalCLITrace.*;

/** Each view passes alone, requires its own evidence, and rejects its own corrupt
 * return without penalizing the other view. */
public final class A2MessageViewsTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 fixture directory");
        Path directory=Path.of(args[0]); F26Assignment2Suite.configureProperties();
        var processor=F26Assignment2Suite.createTraceFilesProcessor(directory);processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        int mutations=0;
        for(var view:List.of(CLIMessageView.ORDINARY,CLIMessageView.SYSTEM)) {
            var other=view==CLIMessageView.ORDINARY?CLIMessageView.SYSTEM:CLIMessageView.ORDINARY;
            require(check(view,records,io,directory).passed(),"Correct "+view);
            var numbers=io.entries().stream().filter(e->CLICommandNames.MESSAGES.equals(e.input().commandName()) && view.includes(e))
                .map(CLIMixedIO.Entry::number).collect(java.util.stream.Collectors.toSet());
            for(String mutation:List.of("missing-input","missing-return","empty-result","system-rule")) {
                var changed=new ArrayList<TraceRecord>();
                for(var record:records) {
                    if(!CLITraceEvidence.is(record,Event.COMMAND_HANDLED) || !numbers.contains(CLITraceEvidence.number(record.getTraceLine().getAuxiliaryData()))) {
                        changed.add(record);continue;
                    }
                    if(mutation.equals("missing-input") || mutation.equals("missing-return")) continue;
                    var line=record.getTraceLine();var fields=new LinkedHashMap<>(line.getAuxiliaryData());
                    String text=new CLIResultEvidence(CLITraceEvidence.resultDump(record)).text();
                    fields.keySet().removeIf(key->key.startsWith(RESULT));
                    if(mutation.equals("empty-result")) text="incorrect-result";
                    else if(view==CLIMessageView.ORDINARY) text+="\n[1.system] forbidden system entry";
                    else for(var entry:io.entries()) {
                        Object system=entry.input().properties().get(eduAI.properties.AIPropertyNames.SYSTEM_PROMPT);
                        if(system instanceof String value && !value.isBlank()) text=text.replace(value,"omitted-system");
                    }
                    fields.put(RESULT+TraceDataPaths.VALUE,text);
                    changed.add(new TraceRecord(record.getId(),new TraceLine(line.getLayerName(),line.getEventName(),line.getThreadPatternName(),
                        line.getThreadName(),line.getDesignPatternName(),line.getSourceClassName(),fields,""),record.getTraceFileName(),record.getTraceFileLineNumber()));
                }
                var changedIO=mutation.equals("missing-input") ? new CLIMixedIO(io.entries().stream().filter(e->!numbers.contains(e.number())).toList()) : io;
                require(!check(view,changed,changedIO,directory).passed(),view+" survived "+mutation);
                var unaffected=check(other,changed,changedIO,directory);
                require(unaffected.passed(),other+" failed because of "+view+" "+mutation+": "+unaffected.level3ErrorMessages());
                mutations++;
            }
            // Relevant exercise must precede this view, not merely occur elsewhere.
            var beforeExercise=new CLIMixedIO(io.entries().stream().filter(e->!numbers.contains(e.number()) ||
                e.number()<io.entries().stream().filter(p->p.input().kind()==CLIInteraction.Kind.PROMPT).mapToInt(CLIMixedIO.Entry::number).sorted().skip(CLISemanticEvidence.MINIMUM_HISTORY_EXCHANGES-1).findFirst().orElseThrow()).toList());
            require(!CLISemanticEvidence.messages(beforeExercise,view).isEmpty(),"Insufficient history accepted for "+view);
            mutations++;
        }
        System.out.println("A2MessageViewsTest passed: correct views; "+mutations+" independent evidence/content mutations");
        System.exit(0);
    }
    private static TraceCheckResult check(CLIMessageView view,Collection<TraceRecord> records,CLIMixedIO io,Path directory) throws Exception {
        var registry=new CLICommandResultCheckerRegistry();
        registry.register(CLICommandNames.MESSAGES,new CLIMessagesCommandResultChecker(io,directory,view,F26Assignment2Suite.attachmentKinds()));
        return CLICommandResultChecks.check(records,io,registry,CLICommandNames.MESSAGES,view::includes);
    }
    private static void require(boolean value,String message) { if(!value) throw new AssertionError(message); }
}
