package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.TraceLineParser;
import eduAI.trace.cli.MinimalCLITrace.Event;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.A2TraceIntegritySuite;
import gradingTools.comp690f26.common.AbstractSessionTraceEventTraced;
import java.nio.file.*;
import java.util.*;


/** Checks suite coverage and removes each required event independently. */
public final class A2TraceIntegritySuiteTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=2) throw new IllegalArgumentException("Expected correct trace and mutation output directory");
        Path original=Path.of(args[0]), output=Path.of(args[1]);
        Files.createDirectories(output);
        var lines=Files.readAllLines(original);
        var nameMethod=AbstractSessionTraceEventTraced.class.getDeclaredMethod("eventName");
        var checkMethod=AbstractSessionTraceEventTraced.class.getDeclaredMethod("check",TraceFilesProcessor.class);
        nameMethod.setAccessible(true);
        checkMethod.setAccessible(true);
        Set<String> covered=new LinkedHashSet<>();
        for(var type:A2TraceIntegritySuite.checks()) {
            Object check=type.getConstructor().newInstance();
            String event=(String)nameMethod.invoke(check);
            if(!covered.add(event)) throw new AssertionError("Duplicate event check: "+event);
            var correct=new NativeTraceFilesProcessor(original);
            correct.processTraceFiles();
            if(!((TraceCheckResult)checkMethod.invoke(check,correct)).passed()) throw new AssertionError("Correct trace rejected: "+event);
            Path mutation=output.resolve("missing_"+event+".txt");
            Files.write(mutation,lines.stream().filter(line->line.isBlank()
                || !event.equals(TraceLineParser.parse(line).getEventName())).toList());
            var changed=new NativeTraceFilesProcessor(mutation);
            changed.processTraceFiles();
            if(((TraceCheckResult)checkMethod.invoke(check,changed)).passed()) throw new AssertionError("Missing event accepted: "+event);
        }
        Set<String> required=new LinkedHashSet<>();
        for(var event:Event.values()) required.add(event.eventName());
        if(!required.equals(covered)) throw new AssertionError("CLI trace suite coverage differs: "+covered);
        System.out.println("PASS "+covered.size()+" event checks and missing-event mutations");
    }
}
