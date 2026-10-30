package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.TraceLineParser;
import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;


/** Replays a recorded session and rejects independent input/trace mutations; never calls models. */
public final class CLISessionChecksTest {
    private static Path directory;
    private static List<String> log;
    private static int checks;
    public static void main(String[] args) throws Exception {
        directory = Path.of(args[0]).toAbsolutePath();
        Path fixtures = args.length > 1 ? Path.of(args[1]) : Files.createTempDirectory("cli-session-fixtures-");
        log = Files.readAllLines(directory.resolve("CLIInputOutput.txt"));
        var p = processor();
        require(p.checkExpectedRequestSettings().passed(), "request settings: " + p.checkExpectedRequestSettings().level3ErrorMessages());
        for (String provider : List.of("Gemini", "Ollama")) {
            require(p.checkExpectedPrompts(provider).passed(), "prompts: " + p.checkExpectedPrompts(provider).level3ErrorMessages());
            require(p.checkExpectedGenericImagesMatch(provider).passed(), "images: " + p.checkExpectedGenericImagesMatch(provider).level3ErrorMessages());
            require(p.checkPreviousUserPromptsRetained(provider).passed(), "history");
            require(p.checkPreviousCompletedResponsesRetained(provider).passed(), "response history");
            require(p.checkStreamingChunksReflectedInNextContextWindow(provider).passed(), "streaming context");
        }
        inputMutation("model", lines -> replace(lines, "/prop current_models = llava", "/prop current_models = wrong-model"), CLITraceExpectations.Check.SETTINGS);
        inputMutation("property", lines -> replace(lines, "/prop temperature = 0.4", "/prop temperature = 0.9"), CLITraceExpectations.Check.SETTINGS);
        inputMutation("mode", lines -> replace(lines, "/prop stream = true", "/prop stream = false"), CLITraceExpectations.Check.SETTINGS);
        inputMutation("prompt", lines -> replace(lines, "How did the same test go for Galahad?", "A different question?"), CLITraceExpectations.Check.PROMPTS);
        inputMutation("system", lines -> replace(lines, "You are a movie expert.", "You are a different expert."), CLITraceExpectations.Check.PROMPTS);
        var alias = expectations(replace(log, "/prop stream", "/prop streaming"));
        require(alias.check(p.getTraceRecords().values(), null, CLITraceExpectations.Check.SETTINGS).passed(), "streaming alias rejected");
        try {
            expectations(replace(log, "Image.png", "missing-image.png"));
            throw new AssertionError("missing attachment accepted");
        } catch (java.io.IOException expected) { checks++; }
        var requests = p.getTraceRecords().values().stream().filter(r -> r.getTraceLine().getEventName().equals("request_sent"))
                .sorted(Comparator.comparingInt(r -> r.getId().sequenceNumber())).toList();
        require(!expectations(log).check(p.getTraceRecords().values().stream()
                .filter(r -> r != requests.get(1)).toList(), null, CLITraceExpectations.Check.SETTINGS).passed(),
                "missing request accepted");
        for (String role : List.of("SYSTEM", "USER", "ASSISTANT")) {
            var mutated = processor();
            var target = mutated.getTraceRecords().get(requests.get(2).getId()); // first switch to Gemini
            var request = (CanonicalTraceRequest) target.getExtractedData().get("providerIndependentCanonicalRequest");
            var messages = new ArrayList<>(request.contextWindow());
            for (int i = 0; i < messages.size(); i++) if (role.equals(messages.get(i).role())) { messages.remove(i); break; }
            target.putExtractedData("providerIndependentCanonicalRequest", new CanonicalTraceRequest(request.model(), request.parameters(), messages));
            boolean passed = switch (role) {
                case "SYSTEM" -> mutated.checkPreviousSystemPromptsRetained("Gemini").passed();
                case "USER" -> mutated.checkPreviousUserPromptsRetained("Gemini").passed();
                default -> mutated.checkPreviousCompletedResponsesRetained("Gemini").passed();
            };
            require(!passed, "lost " + role + " after model switch accepted");
        }
        var lostImage = processor();
        var imageTarget = lostImage.getTraceRecords().get(requests.get(2).getId());
        var imageRequest = (CanonicalTraceRequest) imageTarget.getExtractedData().get("providerIndependentCanonicalRequest");
        var noImages = imageRequest.contextWindow().stream().map(message -> new GenericTracesFileProcessor.TraceMessage(
                message.role(), message.parts().stream().filter(part -> !part.kind().equals("imageBytes")).toList(),
                message.textAggregate())).toList();
        imageTarget.putExtractedData("providerIndependentCanonicalRequest", new CanonicalTraceRequest(
                imageRequest.model(), imageRequest.parameters(), noImages));
        require(!lostImage.checkPreviousImagesRetained("Gemini").passed(), "lost image after model switch accepted");
        Path filtered = fixtures.resolve("correct_filtered");
        Files.createDirectories(filtered);
        List<String> essential = Files.readAllLines(directory.resolve("CLITrace.txt")).stream().filter(line -> {
            var parsed = TraceLineParser.parse(line);
            return parsed != null && EssentialTraceEvents.all().contains(parsed.getEventName());
        }).toList();
        Files.write(filtered.resolve("CLITrace.txt"), essential);
        for (String name : List.of("CLIInputOutput.txt", "Image.png", "A2CLIInput.txt"))
            Files.copy(directory.resolve(name), filtered.resolve(name), StandardCopyOption.REPLACE_EXISTING);
        Set<String> lookedUp = new HashSet<>();
        List<String> cached = essential.stream().filter(line -> {
            var parsed = TraceLineParser.parse(line);
            return !"server_handle_factory_fetched".equals(parsed.getEventName())
                    || lookedUp.add(parsed.getAuxiliaryData().get("provider"));
        }).toList();
        Path cachedFile = fixtures.resolve("cached-factories.txt");
        Files.write(cachedFile, cached);
        var cachedProcessor = new NativeTraceFilesProcessor(cachedFile);
        require(cachedProcessor.checkTraceEventExists("server_handle_factory_fetched", Map.of(
                "Gemini", List.of(StreamingMode.NON_STREAMING, StreamingMode.STREAMING),
                "Ollama", List.of(StreamingMode.NON_STREAMING, StreamingMode.STREAMING))).passed(),
                "one factory lookup per provider was rejected");
        for (String provider : List.of("gemini", "ollama")) {
            var missing = essential.stream().filter(line -> !(line.contains("{server_handle_factory_fetched}")
                    && line.contains("provider=\"" + provider + "\""))).toList();
            Path mutation = fixtures.resolve("mutations").resolve("missing_" + provider + "_lookup");
            Files.createDirectories(mutation);
            Files.write(mutation.resolve("CLITrace.txt"), missing);
            for (String name : List.of("CLIInputOutput.txt", "Image.png", "A2CLIInput.txt"))
                Files.copy(directory.resolve(name), mutation.resolve(name), StandardCopyOption.REPLACE_EXISTING);
            var broken = new NativeTraceFilesProcessor(mutation.resolve("CLITrace.txt"));
            String canonical = provider.equals("gemini") ? "Gemini" : "Ollama";
            require(!broken.checkTraceEventExists("server_handle_factory_fetched",
                    Map.of(canonical, List.of(StreamingMode.NON_STREAMING, StreamingMode.STREAMING))).passed(), "missing lookup accepted");
        }
        System.out.println("CLISessionChecksTest passed: " + checks + " assertions; filtered trace and mutations written.");
    }
    private static NativeTraceFilesProcessor processor() throws Exception {
        var p = new NativeTraceFilesProcessor(directory.resolve("CLITrace.txt"));
        p.processTraceFiles(); p.setExpectedContentChecks(new eduAI.trace.processors.cli.CLIExpectedContentChecks(expectations(log))); return p;
    }
    private static CLITraceExpectations expectations(List<String> lines) throws Exception {
        var result = F26Assignment2Suite.createInteractionsProcessor().processLines(lines);
        require(result.succeeded(), "log parsing: " + result.diagnostics());
        return CLITraceExpectations.from(result.interaction().orElseThrow(), directory);
    }
    private static void inputMutation(String name, Function<List<String>, List<String>> mutation,
            CLITraceExpectations.Check check) throws Exception {
        var p = processor();
        require(!expectations(mutation.apply(log)).check(p.getTraceRecords().values(), null, check).passed(), name + " mutation accepted");
    }
    private static List<String> replace(List<String> lines, String from, String to) {
        return lines.stream().map(line -> line.replace(from, to)).toList();
    }
    private static void require(boolean value, String message) { checks++; if (!value) throw new AssertionError(message); }
}
