package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.cli.MinimalCLITrace;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.*;
import java.util.*;

/** Correct captured session passes. Removing each evidence channel, changing
 * the setting, or omitting either exercised mode must fail independently. */
public final class A2StreamingSettingTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct fixture directory");
        var directory=Path.of(args[0]);
        var processor=F26Assignment2Suite.createTraceFilesProcessor(directory);
        processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        var errors=CLIStreamingSettingChecks.check(records,io);
        require(errors.isEmpty(),"Correct session: "+errors);
        require(CLIStreamingSettingChecks.checkOutput(io).isEmpty(),"Correct I/O-only session");
        int mutations=0;
        for(String event:List.of("partial_response_callback_invoked","stream_output_notified","stream_displayed","command_handled")) {
            reject(records.stream().filter(r->!event.equals(r.getTraceLine().getEventName())).toList(),io,event);mutations++;
        }
        reject(records,new CLIMixedIO(io.entries().stream().map(e->new CLIMixedIO.Entry(e.number(),e.input(),e.attachments(),e.output(),List.of())).toList()),"missing captured chunks");mutations++;
        // Each stream=true prompt is checked separately, not just one per provider/session.
        for(var target:io.entries()) if(!target.chunks().isEmpty()) {
            reject(records,new CLIMixedIO(io.entries().stream().map(e->e.number()==target.number()?new CLIMixedIO.Entry(e.number(),e.input(),e.attachments(),e.output(),List.of()):e).toList()),"one prompt missing output");mutations++;
        }
        for(boolean value:List.of(false,true)) {
            var entries=io.entries().stream().map(e->{
                var input=e.input();
                if(input.kind()!=CLIInteraction.Kind.PROPERTY_SET || !Set.of("stream","streaming").contains(input.propertyName())) return e;
                var properties=new HashMap<String,Object>(input.properties());properties.put(input.propertyName(),value);
                var changed=new CLIInteraction.Command(input.inputOrdinal(),input.lineNumber(),input.rawInput(),input.kind(),input.commandName(),input.argument(),input.propertyName(),properties);
                return new CLIMixedIO.Entry(e.number(),changed,e.attachments(),e.output(),e.chunks());
            }).toList();
            reject(records,new CLIMixedIO(entries),"wrong setting/missing mode "+value);mutations++;
            require(!CLIStreamingSettingChecks.checkOutput(new CLIMixedIO(entries)).isEmpty(),"I/O setting mutation passed");
        }
        for(String mutation:List.of("wrong model","unexpected output","missing setter")) {
            var entries=io.entries().stream().map(e->{
                if(mutation.equals("wrong model") && !e.chunks().isEmpty())
                    return new CLIMixedIO.Entry(e.number(),e.input(),e.attachments(),e.output(),List.of(new CLIMixedIO.Chunk("unselected-model","text")));
                if(mutation.equals("unexpected output") && e.input().kind()!=CLIInteraction.Kind.PROMPT)
                    return new CLIMixedIO.Entry(e.number(),e.input(),e.attachments(),e.output(),List.of(new CLIMixedIO.Chunk("llava","text")));
                return e;
            }).filter(e->!mutation.equals("missing setter") || e.input().kind()!=CLIInteraction.Kind.PROPERTY_SET
                || !Set.of("stream","streaming").contains(e.input().propertyName())).toList();
            var changed=new CLIMixedIO(entries);
            reject(records,changed,mutation);
            require(!CLIStreamingSettingChecks.checkOutput(changed).isEmpty(),"I/O mutation passed: "+mutation);
            mutations++;
        }
        reject(List.of(),new CLIMixedIO(List.of()),"no evidence");mutations++;
        // The shared A1 validator must still reject wrong callback contents.
        // Fresh processors keep recorded failures independent for the two providers.
        for(String provider:List.of("Gemini","Ollama")) {
            var fresh=F26Assignment2Suite.createTraceFilesProcessor(directory);
            fresh.processTraceFiles();
            require(fresh.checkPartialResponseCallbacksInvoked(provider).passed(),"Correct A1 callback evidence: "+provider);
            var callback=fresh.getTraceRecords().values().stream()
                .filter(r->provider.equalsIgnoreCase(r.getId().provider()) && StreamingCallbackEvidence.isPartialCallback(r))
                .findFirst().orElseThrow();
            callback.putExtractedData(StreamingCallbackEvidence.CALLBACK_ARGUMENT_STRUCTURE,
                new GenericTracesFileProcessor.TraceMessage("USER",List.of(new GenericTracesFileProcessor.TracePart("text","wrong callback"))));
            require(!fresh.checkPartialResponseCallbacksInvoked(provider).passed(),"Corrupt callback passed A1 validator: "+provider);
            mutations++;
        }
        System.out.println("A2StreamingSettingTest passed: correct session and "+mutations+" rejected mutations");
        System.exit(0);
    }
    private static void reject(Collection<TraceRecord> records,CLIMixedIO io,String name) {
        require(!CLIStreamingSettingChecks.check(records,io).isEmpty(),"Mutation passed: "+name);
        if(name.equals("missing captured chunks") || name.equals("one prompt missing output") || name.equals("no evidence"))
            require(!CLIStreamingSettingChecks.checkOutput(io).isEmpty(),"I/O mutation passed: "+name);
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
