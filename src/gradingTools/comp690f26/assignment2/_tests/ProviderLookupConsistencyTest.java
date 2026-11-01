package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import java.nio.file.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.processors.mutations.ProviderLookupMutations;
import gradingTools.comp690f26.common.TraceEvidenceView;


/** Swapped lookup keys must fail against native request types in both providers
 * and modes. Other requests keep their own identity credit, even when the full
 * session fails. This uses actual trace mutations, not injected checker results. */
public final class ProviderLookupConsistencyTest {
    private static TraceFilesProcessor read(Path directory) {
        A2ChecksConfiguration.setTraceDirectory(directory.toString());
        A2ChecksConfiguration.configureProperties();
        var processor = A2ChecksConfiguration.createTraceFilesProcessor(directory);
        processor.processTraceFiles();
        return processor;
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Supply correct A2 directory and mutation root");
        Path source = Path.of(args[0]), target = Path.of(args[1]);
        var correct = read(source);
        require(correct.checkProviderNamesConsistent().passed(), "Correct provider evidence rejected");
        var mutations = ProviderLookupMutations.create(Files.readAllLines(source.resolve(CLITraceInformation.TRACE_FILE_NAME)),
            correct.getTraceRecords().values());
        require(mutations.size() == Set.of(TraceProviderNames.GEMINI, TraceProviderNames.OLLAMA).size() * eduAI.trace.lib.StreamingMode.values().length,
            "Expected both providers and modes");
        for (var entry : mutations.entrySet()) {
            Path directory = target.resolve(entry.getKey());
            Files.createDirectories(directory);
            try (var inputs = Files.list(source)) {
                for (Path input : inputs.filter(Files::isRegularFile).toList())
                    Files.copy(input, directory.resolve(input.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            }
            Files.write(directory.resolve(CLITraceInformation.TRACE_FILE_NAME), entry.getValue().lines());
            var changed = read(directory);
            var result = changed.checkProviderNamesConsistent();
            require(!result.passed(), "Wrong provider not rejected: " + entry.getKey() + " " + result);
            int rejected = 0, retained = 0;
            for (var request : changed.getTraceRecords().values()) {
                if (!LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT).equals(request.getTraceLine().getEventName())) continue;
                var scoped = TraceEvidenceView.select(changed, r -> r == request);
                boolean passed = scoped.checkProviderNamesConsistent().passed();
                if (request.getTraceFileLineNumber() == entry.getValue().requestLine()) {
                    require(!passed, "Relevant wrong lookup earned credit");
                    rejected++;
                } else {
                    require(passed, "Unrelated request blocked at " + request.getTraceFileLineNumber() + " in " + entry.getKey());
                    retained++;
                }
            }
            require(rejected == 1 && retained > 0, "Missing scope controls");
            System.out.println("VERIFIED " + entry.getKey() + ": affected request rejected; " + retained + " unrelated requests pass");
        }
        Path swapped = target.resolve("swapped_provider_bindings");
        Files.createDirectories(swapped);
        Files.write(swapped.resolve(CLITraceInformation.TRACE_FILE_NAME), ProviderLookupMutations.swapAllProviderKeys(
            Files.readAllLines(source.resolve(CLITraceInformation.TRACE_FILE_NAME)), correct.getTraceRecords().values()));
        var swappedProcessor = read(swapped);
        var mismatch = swappedProcessor.checkProviderNamesConsistent();
        require(!mismatch.passed() && mismatch.level3ErrorMessages().stream().anyMatch(s -> s.contains("Request native provider evidence")),
            "Consistently swapped providers were not rejected by independent native evidence: " + mismatch);
        for (var request : swappedProcessor.getTraceRecords().values()) {
            if (!LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT).equals(request.getTraceLine().getEventName())) continue;
            var scoped = TraceEvidenceView.select(swappedProcessor, r -> r == request).checkProviderNamesConsistent();
            require(!scoped.passed() && scoped.level3ErrorMessages().stream().anyMatch(s -> s.contains("Request native provider evidence")),
                "Scoped swapped provider not rejected at " + request.getTraceFileLineNumber());
        }
        System.out.println("VERIFIED consistently swapped providers rejected by native request evidence");
    }
}
