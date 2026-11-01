package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import java.nio.file.*;
import eduAI.trace.processors.mutations.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;

import eduAI.trace.lib.StreamingMode;


/** Generates only realistic key collisions from correct snapshots. Both providers
 * and modes must reject their collision while the other provider and mode retain
 * metadata credit. No grading prerequisite or score is changed. */
public final class MetadataCollisionFixturesTest {
    private static TraceFilesProcessor read(boolean cli, Path directory) {
        var processor = cli ? A2ChecksConfiguration.createTraceFilesProcessor(directory)
            : A1ChecksConfiguration.createTraceFilesProcessor(directory);
        processor.processTraceFiles();
        return processor;
    }
    private static void verify(boolean cli, Path source, Path target) throws Exception {
        if (!read(cli,source).checkProviderGenericMetadataMatches().passed())
            throw new AssertionError("Correct metadata rejected: "+source);
        var files = cli ? List.of(CLITraceInformation.TRACE_FILE_NAME) : TraceFixtureFilter.TRACE_FILE_NAMES;
        Set<String> tested = new LinkedHashSet<>();
        for (String file : files) for (var mutation : MetadataKeyCollisionMutations.create(
                Files.readAllLines(source.resolve(file))).entrySet()) {
            Path directory = target.resolve(mutation.getKey());
            Files.createDirectories(directory);
            try (var inputs = Files.list(source)) {
                for (Path input : inputs.filter(Files::isRegularFile).toList())
                    Files.copy(input,directory.resolve(input.getFileName()),StandardCopyOption.REPLACE_EXISTING);
            }
            Files.write(directory.resolve(file),mutation.getValue());
            String provider = mutation.getKey().contains(TraceProviderNames.OLLAMA.toLowerCase(Locale.ROOT))
                ? TraceProviderNames.OLLAMA : TraceProviderNames.GEMINI;
            String other = provider.equals(TraceProviderNames.OLLAMA) ? TraceProviderNames.GEMINI : TraceProviderNames.OLLAMA;
            var mode = mutation.getKey().endsWith(StreamingMode.NON_STREAMING.name().toLowerCase(Locale.ROOT))
                ? StreamingMode.NON_STREAMING : StreamingMode.STREAMING;
            var failure = read(cli,directory).checkProviderGenericMetadataMatches(provider,mode);
            if (failure.passed() || failure.level3ErrorMessages().stream().noneMatch(
                    s -> s.contains(MetadataKeyCollisionMutations.DIAGNOSTIC))) throw new AssertionError(directory+": "+failure);
            if (!read(cli,directory).checkProviderGenericMetadataMatches(other).passed())
                throw new AssertionError("Unrelated provider blocked: "+directory);
            var otherMode = mode == StreamingMode.STREAMING ? StreamingMode.NON_STREAMING : StreamingMode.STREAMING;
            if (!read(cli,directory).checkProviderGenericMetadataMatches(provider,otherMode).passed())
                throw new AssertionError("Unrelated mode blocked: "+directory);
            tested.add(provider+" "+mode);
            System.out.println("VERIFIED "+directory+"; other provider and mode pass");
        }
        for (String provider : List.of(TraceProviderNames.OLLAMA,TraceProviderNames.GEMINI))
            for (var mode : StreamingMode.values()) if (!tested.contains(provider+" "+mode))
                throw new AssertionError("Missing collision coverage: "+provider+" "+mode);
    }
    public static void main(String[] args) throws Exception {
        if (args.length!=4) throw new IllegalArgumentException("Supply A1 correct, A2 correct, A1 mutation root, A2 mutation root");
        verify(false,Path.of(args[0]),Path.of(args[2]));
        verify(true,Path.of(args[1]),Path.of(args[3]));
        System.out.println("MetadataCollisionFixturesTest passed: A1/A2 baselines and eight real mutations with scope controls");
    }
}
