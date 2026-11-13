package gradingTools.comp690f26.assignment2._tests;

import java.nio.file.Path;
import java.util.List;
import eduAI.trace.processors.*;
import eduAI.trace.processors.cli.CLIArchitectureChecks;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;
import gradingTools.comp690f26.common.*;

/** Diagnostics identify missing evidence without claiming source classes are absent. */
public final class A2DiagnosticsTest {
    private static final class Summary extends AbstractTraceProcessorCheck {
        protected TraceCheckResult check(TraceFilesProcessor processor) { throw new UnsupportedOperationException(); }
        String message(TraceCheckResult result) { return shortFailureMessage(result); }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("Supply the saved student fixture directory");
        Path directory = Path.of(args[0]);
        A2ChecksConfiguration.configureProperties();
        String syntax = A2ChecksConfiguration.createInteractionsProcessor().process(
            directory.resolve(eduAI.trace.processors.CLITraceInformation.INTERACTIONS_FILE_NAME)).toString();
        require(syntax.contains("received: > /prop") && syntax.contains("[1] /prop stream")
            && syntax.contains("@@start"), syntax);
        var processor = new NativeTraceFilesProcessor(directory.resolve(eduAI.trace.processors.CLITraceInformation.TRACE_FILE_NAME));
        processor.processTraceFiles();
        String role = AbstractClassFoundInTrace.missingRoleMessage(CLIArchitectureChecks.EXIT_HANDLER_TAG, processor);
        require(role.contains("does not establish") && role.contains("'bye'") && role.contains("exit"), role);
        String detail = "native timeout target is not recognized";
        String summary = new Summary().message(new TraceCheckResult(List.of(), List.of(), List.of(detail)));
        require(summary.contains(detail), summary);
        System.out.println("A2DiagnosticsTest passed");
    }
    private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}

