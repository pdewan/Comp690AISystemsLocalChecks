package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.processors.*;
import grader.basics.junit.*;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import java.nio.file.*;
import java.util.*;


import static gradingTools.comp690f26.assignment2._tests.A1A2ScopeRegressionTest.*;

/** Both provider-switch endpoints are checked. Mistranslated native text
 * rejects textual retention while image retention remains independent. */
public final class CrossProviderHistoryPrerequisitesTest {
    private static final String HISTORY_PACKAGE = "gradingTools.comp690f26.assignment1.contextHistorySuite.";
    private static final java.util.regex.Pattern TEXT_LENGTH = java.util.regex.Pattern.compile("(@text:)([0-9]+)(:)");
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply correct A2 directory");
        Path source = Path.of(args[0]);
        F26Assignment2Suite.setTraceDirectory(source.toString());
        var processor = F26Assignment2Suite.createTraceFilesProcessor(source);
        processor.processTraceFiles();
        var requests = processor.getTraceRecords().values().stream()
            .filter(r -> LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT).equals(r.getTraceLine().getEventName()))
            .sorted(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber)).toList();
        var original = Files.readAllLines(source.resolve(CLITraceInformation.TRACE_FILE_NAME));
        var directory = Files.createTempDirectory(Path.of("build"), "cross-provider-history-");
        Path trace = directory.resolve(CLITraceInformation.TRACE_FILE_NAME);
        F26Assignment2Suite.setTraceDirectory(directory.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
        var directions = new HashSet<String>();
        for (int index = 1; index < requests.size(); index++) {
            var before = requests.get(index - 1); var after = requests.get(index);
            if (before.getId().provider().equals(after.getId().provider())) continue;
            String provider = after.getId().provider();
            if (!directions.add(provider)) continue;
            var checks = new ArrayList<Class<?>>();
            for (String suffix : List.of("PreviousCompletedResponsesRetained", "PreviousUserPromptsRetained", "PreviousSystemPromptsRetained", "PreviousImagesRetained"))
                checks.add(Class.forName(HISTORY_PACKAGE + provider + suffix));
            Files.write(trace, original); JUnitTestsEnvironment.clearCachedJUnitTestCases();
            for (var check : checks) full(check);
            var consumed = processor.historyRequestEvidence(provider);
            require(consumed.contains(before) && consumed.contains(after), "Both endpoints must be selected");
            for (var endpoint : List.of(before, after)) {
                // Present but mistranslated text must fail on either endpoint.
                // Image retention must remain independent of that text defect.
                mutate(trace, original, endpoint, CrossProviderHistoryPrerequisitesTest::wrongNativeText);
                for (var check : checks)
                    if (check.getSimpleName().endsWith("PreviousImagesRetained")) full(check);
                    else reject(check);
            }
            System.out.println("Cross-provider history controls passed: " + before.getId().provider() + " -> " + provider);
        }
        require(directions.equals(Set.of(TraceProviderNames.OLLAMA, TraceProviderNames.GEMINI)), "Both provider directions required");
        System.out.println("CrossProviderHistoryPrerequisitesTest passed: both endpoints/directions, textual rejection and image independence");
        System.exit(0);
    }
    private static String wrongNativeText(String line) {
        int start = line.indexOf(RequestTraceFields.NATIVE_CONTEXT + "=");
        require(start >= 0, "Missing native context in mutation baseline");
        String nativeDump = line.substring(start);
        var matcher = TEXT_LENGTH.matcher(nativeDump);
        require(matcher.find(), "No native text summary to mutate");
        String replacement = matcher.group(1) + (Integer.parseInt(matcher.group(2)) + 1) + matcher.group(3);
        return line.substring(0, start) + matcher.replaceFirst(java.util.regex.Matcher.quoteReplacement(replacement));
    }
}
