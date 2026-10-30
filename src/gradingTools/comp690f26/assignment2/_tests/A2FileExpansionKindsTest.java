package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.TraceLine;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.*;
import java.util.*;


import static eduAI.trace.cli.MinimalCLITrace.*;

/** Both correct kinds pass; a missing, duplicate or misnamed expansion
 * fails only its own kind. An absent input kind earns no credit. */
public final class A2FileExpansionKindsTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply the correct A2 directory");
        Path directory=Path.of(args[0]);
        F26Assignment2Suite.configureProperties();
        var processor=F26Assignment2Suite.createTraceFilesProcessor(directory);
        processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        var io=F26Assignment2Suite.createInteractionsProcessor().process(
            directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        for(var kind:CLIFileExpansionContentsChecks.Kind.values()) {
            require(check(kind,records,io,directory),"Correct "+kind);
            var selected=records.stream().filter(r->CLITraceEvidence.is(r,Event.FILE_EXPANDED))
                .filter(r->{
                    try { return CLIFileExpansionContentsChecks.readAttachment(directory,
                        r.getTraceLine().getAuxiliaryData().get(INPUT_FILE_NAME)).image()
                        ==(kind==CLIFileExpansionContentsChecks.Kind.IMAGE); }
                    catch(java.io.IOException e) { throw new java.io.UncheckedIOException(e); }
                }).findFirst().orElseThrow();
            var other=kind==CLIFileExpansionContentsChecks.Kind.IMAGE
                ? CLIFileExpansionContentsChecks.Kind.TEXT : CLIFileExpansionContentsChecks.Kind.IMAGE;
            for(String fault:List.of("missing","duplicate","filename")) {
                var changed=new ArrayList<>(records);
                if(fault.equals("missing")) changed.remove(selected);
                else if(fault.equals("duplicate")) changed.add(selected);
                else {
                    var fields=new LinkedHashMap<>(selected.getTraceLine().getAuxiliaryData());
                    switch(fault) {
                        case "filename" -> fields.put(INPUT_FILE_NAME,"unrequested-file");
                        default -> throw new AssertionError(fault);
                    }
                    var line=selected.getTraceLine();
                    var replacement=new TraceRecord(selected.getId(),new TraceLine(line.getLayerName(),line.getEventName(),
                        line.getThreadPatternName(),line.getThreadName(),line.getDesignPatternName(),line.getSourceClassName(),fields,""),
                        selected.getTraceFileName(),selected.getTraceFileLineNumber());
                    changed.set(changed.indexOf(selected),replacement);
                }
                require(!check(kind,changed,io,directory),kind+" mutation survived: "+fault);
                require(check(other,changed,io,directory),other+" rejected unrelated "+kind+" "+fault);
            }
            require(!check(kind,records,new CLIMixedIO(List.of()),directory),"Absent input kind earned credit");
        }
        System.out.println("A2FileExpansionKindsTest passed: both kinds; 8 independent mutations; absent-kind gates");
        System.exit(0);
    }
    private static boolean check(CLIFileExpansionContentsChecks.Kind kind,Collection<TraceRecord> records,CLIMixedIO io,Path directory) {
        return CLIFileExpansionContentsChecks.check(kind,records,io,directory,F26Assignment2Suite.attachmentKinds()).passed();
    }
    private static void require(boolean condition,String message) { if(!condition) throw new AssertionError(message); }
}
