package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/** Real trace mutations: generic keys/layout may change; native values may not.
 * A renamed or field-only store succeeds. A wrong/missing native value fails even
 * when the expected number appears elsewhere in the native configuration. */
public final class ParameterKeyIndependenceTest {
    private static final List<String> failures = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Path output = Path.of(args[0]); Files.createDirectories(output);
        for (String provider : List.of("Gemini", "Ollama")) {
            for (String key : List.of("temperature", "samplingWarmth")) {
                check(provider + " renamed/field store " + key,
                    parameter(output, provider, key, "0.4", "0.4"), true);
                check(provider + " wrong native value " + key,
                    parameter(output, provider, key, "0.4", "0.9"), false);
                check(provider + " wrong generic value " + key,
                    parameter(output, provider, key, "0.9", "0.4"), false);
            }
            check(provider + " named map wrong native value", parameter(output, provider, "temperature", "0.4", "0.9", true), false);
        }
        Path fixture = Path.of(args[1]);
        String original = Files.readString(fixture.resolve("CLITrace.txt"));
        // Only change library-side property names and the generic store dump.
        // CLI commands and known native SDK fields retain their specified names.
        StringBuilder changed = new StringBuilder();
        for (String line : original.lines().toList()) {
            if (line.startsWith("## lib ")) {
                var data = new LinkedHashMap<>(eduAI.trace.TraceLineParser.parse(line).getAuxiliaryData());
                data.computeIfPresent("propertyName", (k, v) -> renamed(v));
                data.computeIfPresent("providerIndependentParameterStore", (k, dump) -> {
                    var leaves = new ArrayList<Object>();
                    for (var leaf : new ReflectiveValueCollector().collect(dump)) {
                        leaves.add(leaf.kind() == ReflectiveValueCollector.ValueKind.NUMBER
                            ? new java.math.BigDecimal(leaf.value()) : renamed(leaf.value()));
                    }
                    // Reprint a real alternate object so summaries and leaf values agree.
                    return eduAI.trace.TraceObjectPrinter.format(k, new ParameterBag(leaves));
                });
                int start = line.indexOf(" <"), end = line.lastIndexOf("> ##");
                line = line.substring(0, start + 2) + eduAI.trace.GenericTrace.formatAuxiliaryData(data) + line.substring(end);
            }
            changed.append(line).append('\n');
        }
        Path renamed = output.resolve("renamed-CLITrace.txt"); Files.writeString(renamed, changed);
        var processor = new NativeTraceFilesProcessor(renamed); processor.processTraceFiles();
        var io = gradingTools.comp690f26.assignment2.A2ChecksConfiguration.createInteractionsProcessor().process(fixture.resolve("CLIInputOutput.txt")).interaction().orElseThrow();
        var settings = CLITraceExpectations.settingsFrom(io)
            .check(processor.getTraceRecords().values(), null, CLITraceExpectations.Check.SETTINGS);
        if (!settings.passed()) System.out.println(settings.level3ErrorMessages());
        check("A2 settings with arbitrary generic keys", settings.passed(), true);
        for (String provider : List.of("Gemini", "Ollama"))
                check("A2 shared parameter translation " + provider,
                    processor.checkProviderGenericParameterMatches(provider).passed(), true);
        for (String provider : List.of("Gemini", "Ollama")) {
            Map<String, String> mutations = provider.equals("Gemini")
                ? Map.of("temperature", "0.13", "topK", "7", "timeout", "17000")
                : Map.of("temperature", "0.13", "top_k", "7", "num_ctx", "4096", "requestTimeoutSeconds", "17");
            for (var mutation : mutations.entrySet()) {
                String bad = nativeMutation(original, provider, mutation.getKey(), mutation.getValue());
                if (bad.equals(original)) throw new AssertionError("Mutation did not change native evidence: " + mutation);
                Path file = output.resolve(provider + "-wrong-" + mutation.getKey() + ".txt"); Files.writeString(file, bad);
                var p = new NativeTraceFilesProcessor(file); p.processTraceFiles();
                check("A2 rejects native " + provider + " " + mutation.getKey(), CLITraceExpectations.settingsFrom(io)
                    .check(p.getTraceRecords().values(), null, CLITraceExpectations.Check.SETTINGS).passed(), false);
                check("Shared translation rejects native " + provider + " " + mutation.getKey(),
                    p.checkProviderGenericParameterMatches(provider).passed(), false);
                String other = provider.equals("Gemini") ? "Ollama" : "Gemini";
                check("Unrelated " + other + " retains credit", p.checkProviderGenericParameterMatches(other).passed(), true);
            }
        }
        for (var mutation : eduAI.trace.processors.mutations.CLIRequestInputMutations.create(original.lines().toList(), io, fixture).entrySet()) {
            if (mutation.getValue().check() != CLITraceExpectations.Check.SETTINGS) continue;
            Path file = output.resolve(mutation.getKey() + ".txt"); Files.write(file, mutation.getValue().lines());
            var p = new NativeTraceFilesProcessor(file); p.processTraceFiles();
            check(mutation.getKey() + " unchanged native settings", CLITraceExpectations.settingsFrom(io)
                .check(p.getTraceRecords().values(), null, CLITraceExpectations.Check.SETTINGS).passed(), true);
            check(mutation.getKey() + " rejected by translation prerequisite", p.checkProviderGenericParameterMatches().passed(), false);
        }
        if (!failures.isEmpty()) throw new AssertionError(String.join("\n", failures));
        System.out.println("Parameter key/layout regressions passed");
    }
    private record ParameterBag(List<Object> settings) { }
    private static String renamed(String name) {
        return switch (name) {
            case "timeout_milliseconds" -> "waitMillis";
            case "timeout" -> "waitSeconds";
            case "temperature" -> "warmth";
            case "top_k" -> "sampleLimit";
            case "num_ctx" -> "memoryLimit";
            case "model" -> "engine";
            default -> name;
        };
    }
    private static String nativeMutation(String trace, String provider, String field, String value) {
        Pattern attributes = Pattern.compile("(providerDependentConfiguration|providerDependentTargetState)=\"((?:\\\\.|[^\"\\\\])*+)\"");
        StringBuilder result = new StringBuilder();
        for (String line : trace.lines().toList()) {
            boolean nativeProvider = provider.equals("Gemini") ? line.contains("com.google.genai.")
                : line.contains("io.github.ollama4j.");
            if (nativeProvider && line.startsWith("## lib ")) {
                line = attributes.matcher(line).replaceAll(m -> Matcher.quoteReplacement(m.group(1) + "=\""
                    + m.group(2).replaceAll("(\\b" + Pattern.quote(field) + "=(?:Optional\\[)?)[0-9.]+", "$1" + value) + "\""));
            }
            result.append(line).append('\n');
        }
        return result.toString();
    }
    private static boolean parameter(Path dir, String provider, String key, String stored, String nativeValue) throws Exception {
        return parameter(dir, provider, key, stored, nativeValue, false);
    }
    private record GenericSettings(double arbitraryField) { }
    private static boolean parameter(Path dir, String provider, String key, String stored, String nativeValue, boolean map) throws Exception {
        String target = provider.equals("Gemini") ? "com.google.genai.types.GenerateContentConfig$Builder" : "io.github.ollama4j.utils.OptionsBuilder";
        String state = provider.equals("Gemini") ? "GenerateContentConfig{temperature=Optional[" + nativeValue + "]}"
            : "Options(optionsMap={temperature=" + nativeValue + "})";
        String store = eduAI.trace.TraceObjectPrinter.format("store", map ? Map.of(key, Double.valueOf(stored))
            : new GenericSettings(Double.parseDouble(stored)));
        String trace = event("server_handle_factory_fetched", "provider=" + quote(provider))
            + event("parameter_translated", "parameterHandlerClass=\"sample.Adapter\" propertyName=" + quote(key)
                + " propertyValue=\"0.4\" providerDependentTarget=" + quote(target) + " providerDependentTargetState=" + quote(state))
            + event("request_sent", "provider=" + quote(provider) + " providerIndependentParameterStore=" + quote(store)
                + " providerIndependentContextWindow=\"elements=[]\" providerDependentConfiguration=" + quote(state)
                + " providerDependentContextWindow=\"[]\"");
        Path file = dir.resolve(provider + "-" + key + "-" + stored + "-" + nativeValue + (map ? "-map" : "-field") + ".txt"); Files.writeString(file, trace);
        var p = new NativeTraceFilesProcessor(); p.processTraceFile(provider, StreamingMode.NON_STREAMING, file);
        return p.checkProviderGenericParameterMatches(provider).passed();
    }
    private static String event(String name, String data) {
        return "## lib {" + name + "} [none: main] (single_model_request_processing: sample.Client) <" + data + "> ##\n";
    }
    private static String quote(String value) { return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\""; }
    private static void check(String label, boolean actual, boolean expected) {
        System.out.println(label + " actual=" + actual + " expected=" + expected);
        if (actual != expected) failures.add(label + " actual=" + actual + " expected=" + expected);
    }
}

