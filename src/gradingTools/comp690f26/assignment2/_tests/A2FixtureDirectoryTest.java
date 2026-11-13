package gradingTools.comp690f26.assignment2._tests;

import java.nio.file.*;
import java.lang.reflect.Method;
import java.util.*;
import eduAI.trace.TraceLineParser;
import eduAI.trace.cli.MinimalCLITrace;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;
import gradingTools.comp690f26.assignment2.parameterHistorySuite.A2CLIParameterHistorySnapshotsPreserved;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import gradingTools.comp690f26.files.FilesProvidedSupport;

/** Exercises the real fixture harness from an unrelated configured directory.
 * Correct snapshots pass, omitted history notifications fail, and returning to
 * the correct dataset passes again. A one-time directory setting cannot pass. */
public final class A2FixtureDirectoryTest {
    public static void main(String[] args) throws Exception {
        Path source = Path.of(args[0]), root = Path.of(args[1]);
        Path correct = root.resolve("correct"), missing = root.resolve("missing-history");
        for (Path target : List.of(correct, missing)) {
            Files.createDirectories(target);
            for (String file : List.of(CLITraceInformation.TRACE_FILE_NAME, CLITraceInformation.INTERACTIONS_FILE_NAME,
                    A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT, A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT))
                Files.copy(source.resolve(file), target.resolve(file), StandardCopyOption.REPLACE_EXISTING);
        }
        Path trace = missing.resolve(CLITraceInformation.TRACE_FILE_NAME);
        var lines = Files.readAllLines(trace);
        var changed = lines.stream().filter(line -> !MinimalCLITrace.Event.HISTORY_WITH_PARAMETERS_UPDATED.eventName()
            .equals(TraceLineParser.parse(line).getEventName())).toList();
        if (changed.size() == lines.size()) throw new AssertionError("Missing history mutation target");
        Files.write(trace, changed);
        A2ChecksConfiguration.setTraceDirectory(missing.toString());
        Method check = AbstractTraceProcessorCheck.class.getDeclaredMethod("check", TraceFilesProcessor.class);
        check.setAccessible(true);
        Method harness = A2TraceFixturesTest.class.getDeclaredMethod("failure", Path.class, Set.class, Method.class);
        harness.setAccessible(true);
        for (Path dataset : List.of(correct, missing, correct)) {
            Object failure = harness.invoke(null, dataset, Set.of(A2CLIParameterHistorySnapshotsPreserved.class), check);
            boolean shouldPass = dataset.equals(correct);
            if ((failure == null) != shouldPass) throw new AssertionError(dataset + ": " + failure + "; expected pass=" + shouldPass);
            if (!FilesProvidedSupport.resolvedDirectory().toAbsolutePath().normalize().equals(dataset.toAbsolutePath().normalize()))
                throw new AssertionError("Harness retained another dataset's directory");
            System.out.println("PASS " + dataset + (shouldPass ? " accepted" : " missing history rejected"));
        }
    }
}
