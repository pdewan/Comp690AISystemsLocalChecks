package gradingTools.comp690f26.assignment2._tests;
import java.nio.file.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import eduAI.trace.processors.mutations.CLISixCallMutations;


/** Verifies that file structure and content faults are rejected by separate checks. */
public final class A2StructureSemanticsTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Supply correct and mutation directories");
        Path correct = Path.of(args[0]), mutations = Path.of(args[1]);
        A2ChecksConfiguration.configureProperties();
        var io = A2ChecksConfiguration.createInteractionsProcessor().process(
            correct.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        verify(mutations, io, "expander-instances", CLIArchitectureChecks.Check.FILE_SEPARATION,
            CLIArchitectureChecks.Check.FILE_CONTENTS);
        verify(mutations, io, "attachment-content", CLIArchitectureChecks.Check.FILE_CONTENTS,
            CLIArchitectureChecks.Check.FILE_SEPARATION);
        System.out.println("A2StructureSemanticsTest passed: shared expanders and wrong file contents fail only their own domain");
    }
    private static void verify(Path mutations, CLIMixedIO io, String diagnostic,
            CLIArchitectureChecks.Check failing, CLIArchitectureChecks.Check passing) throws Exception {
        Path selected = null;
        for (String name : Files.readAllLines(mutations.resolve("datasets.txt"))) {
            Path dataset = mutations.resolve(name), expectation = dataset.resolve(CLISixCallMutations.EXPECTATION_FILE);
            if (Files.exists(expectation) && Files.readString(expectation).strip().equals(diagnostic)) {
                selected = dataset;
                break;
            }
        }
        if (selected == null) throw new AssertionError("Missing mutation: " + diagnostic);
        var processor = new NativeTraceFilesProcessor(selected.resolve(CLITraceInformation.TRACE_FILE_NAME));
        processor.processTraceFiles();
        var records = processor.getTraceRecords().values();
        var rejected = CLIArchitectureChecks.check(failing, records, io, selected);
        if (rejected.passed()) throw new AssertionError("Mutation survived " + failing);
        var accepted = CLIArchitectureChecks.check(passing, records, io, selected);
        if (!accepted.passed()) throw new AssertionError("Unrelated domain rejected " + diagnostic + ": " + accepted);
    }
}
