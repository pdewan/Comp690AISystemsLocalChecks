package gradingTools.comp690f26.assignment1._tests;

import grader.basics.junit.*;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment1.interfacesSuite.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;


/** Generates real trace mutations. Absent optional roles must not reduce regular
 * interface points; a traced optional class missing from the project must block
 * both checks. Each variant runs in a fresh JVM to avoid cached grading results. */
public final class A1OptionalPrerequisitesTest {
    private enum Variant {
        ABSENT("", ""),
        MISSING_PROJECT_CLASS("streaming_chunk_accumulated", "messageMergerClass"),
        MISSING_FACTORY("message_merger_factory_fetched", "streamChunkMergerFactoryClass"),
        MISSING_REGISTRY("message_merger_factory_fetched", "streamChunkMergerFactoryRegistryClass");
        final String event, field;
        Variant(String event,String field) { this.event=event; this.field=field; }
    }
    private static final Set<String> OPTIONAL_EVENTS=Set.of("message_merger_factory_fetched",
        "streaming_chunk_accumulated","streaming_chunks_merged");
    public static void main(String[] args) throws Exception {
        if(args.length!=3) throw new IllegalArgumentException("Supply correct directory, mutation directory, and variant");
        Path correct=Path.of(args[0]), target=Path.of(args[1]); Variant variant=Variant.valueOf(args[2]);
        if(correct.toAbsolutePath().normalize().equals(target.toAbsolutePath().normalize()))
            throw new IllegalArgumentException("Mutation directory must differ from correct directory");
        Files.createDirectories(target);
        int changes=0;
        try(var files=Files.list(correct)) {
            for(var file:files.filter(p->p.getFileName().toString().startsWith("Trace")).toList()) {
                List<String> lines=new ArrayList<>();
                for(String original:Files.readAllLines(file)) {
                    if(variant==Variant.ABSENT && OPTIONAL_EVENTS.stream().anyMatch(event->original.contains("{"+event+"}"))) { changes++; continue; }
                    String line=original;
                    if(variant!=Variant.ABSENT && line.contains("{"+variant.event+"}")) {
                        var matcher=Pattern.compile(variant.field+"=\"([^\"]+)\"").matcher(line);
                        if(!matcher.find()) throw new AssertionError("Mutation requires merger class evidence");
                        line=line.replace(matcher.group(1),"mutation.MissingMerger"); changes++;
                    }
                    lines.add(line);
                }
                Files.write(target.resolve(file.getFileName()),lines);
            }
        }
        if(changes==0) throw new AssertionError("No mutation applied");
        F26Assignment1Suite.setTraceDirectory(target.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
        for(var type:List.of(AllTaggedClassesImplementSingleInterface.class,TaggedClassPhysicalStructuresUseInterfaces.class)) {
            var check=JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(type);
            if(check.getLastResult().isPass()!=(variant==Variant.ABSENT))
                throw new AssertionError(variant+" incorrect prerequisite result: "+type+" "+check.getLastResult());
            if(variant==Variant.ABSENT && check.getLastResult().getPercentage()!=1)
                throw new AssertionError("Optional absence scaled regular points");
        }
        System.out.println("A1OptionalPrerequisitesTest passed: "+variant+", both regular interface checks");
        System.exit(0);
    }
}
