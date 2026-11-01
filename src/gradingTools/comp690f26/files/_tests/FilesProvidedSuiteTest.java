package gradingTools.comp690f26.files._tests;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveGeminiExpectedPromptsInProviderRequests;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/** File-pair selection and actual prerequisite results; no student code or model calls. */
public final class FilesProvidedSuiteTest {
	private static final String PROMPT = "Look at \\@\"Image.png\".  Keep these spaces.";
	private static final String LOG = """
			[1] /prop current_models = any-student-model
			[2] /prop system_prompt = Be concise.
			[3] /prop temperature = 0.4
			[4] /prop timeout = 300
			[5] /prop stream = true
			Context window compacted: policy=embedding_similarity, removed_commands=[12]
			@@STREAM[any-student-model] arbitrary chunk
			[6] %s
			@@start [6] success=true conversation_id=A2
			[99] This is output, not another input.
			@@end [6] (Time: 00:00:01.000)
			""".formatted(PROMPT);

	public static void main(String[] args) throws Exception {
		Path root = Files.createTempDirectory("files-provided-suite-");
		try {
			A1ChecksConfiguration.configureProperties();
			select(root);
			CLIOuterOutputSyntax notRequired = new CLIOuterOutputSyntax();
			require(notRequired.test(null, false).isPass(), "A1 required CLI I/O");
			require(notRequired.getInteractions().isEmpty(), "A1 fabricated interactions");
			require(!new ExpectedCheckProbe().requiresInteractions(), "A1 gained a CLI dependency");
			A2ChecksConfiguration.configureProperties();
			require(new ExpectedCheckProbe().requiresInteractions(), "A2 expectations lack their prerequisite");

			Path complete = Files.createDirectory(root.resolve("complete"));
			Files.writeString(complete.resolve("CLITrace.txt"), "");
			Files.writeString(complete.resolve("CLIInputOutput.txt"), LOG);
			select(complete);
			CLIOuterOutputSyntax io = new CLIOuterOutputSyntax();
			require(io.test(null, false).isPass(), "Valid interactions rejected");
			var entries = io.getInteractions().orElseThrow().entries();
			require(entries.size() == 6, "Output body was interpreted as input");
			var prompt = entries.get(5);
			require(prompt.input().rawInput().equals(PROMPT), "Prompt changed");
			require(prompt.attachments().get(0).path().equals("Image.png"), "Attachment path changed");
			require(prompt.input().properties().get("timeout").equals(300), "Timeout expectation missing");
			require(prompt.input().properties().get("stream").equals(true), "Stream expectation missing");
			require(prompt.input().properties().get("current_models").equals(List.of("any-student-model")), "Model constrained to instructor's model");
			require(prompt.chunks().size() == 1, "Streaming output lost");
			require(tracePasses(), "Trace rejected after successful I/O prerequisite");

			Path traceOnly = Files.createDirectory(root.resolve("trace-only"));
			Files.writeString(traceOnly.resolve("CLITrace.txt"), "");
			select(traceOnly);
			var missingResult = io.test(null, false);
			require(!missingResult.isPass(), "Missing I/O accepted");
			String missingMessage = missingResult.getNotes();
			require(missingMessage.contains("Required CLI input/output log not found"), "Missing-file diagnosis unclear");
			require(missingMessage.contains(traceOnly.resolve("CLIInputOutput.txt").toAbsolutePath().normalize().toString()), "Expected absolute path missing");
			require(missingMessage.contains("console transcript") && missingMessage.contains("same run"), "Missing-file recovery guidance absent");
			require(!missingMessage.contains("line 0"), "File error displayed as a source line");
			require(io.getInteractions().isEmpty(), "Stale expectations survived failure");
			require(tracePasses(), "Missing I/O blocked trace-only checking");

			for (String invalid : List.of("", "unexpected unmarked output", "[1] /prop stream = perhaps", "[1] prompt\n@@start [1] success=true conversation_id=A2")) {
				Files.writeString(traceOnly.resolve("CLIInputOutput.txt"), invalid);
				select(traceOnly);
				var invalidResult = io.test(null, false);
				require(!invalidResult.isPass(), "Malformed or empty I/O accepted");
				if (!invalid.isEmpty()) require(invalidResult.getNotes().contains("line 1"), "Parser source line lost");
				require(io.getInteractions().isEmpty(), "Partial expectations escaped");
				require(tracePasses(), "Malformed I/O blocked trace-only checking");
			}
			Path directoryLog = Files.createDirectory(root.resolve("directory-log"));
			Files.writeString(directoryLog.resolve("CLITrace.txt"), "");
			Files.createDirectory(directoryLog.resolve("CLIInputOutput.txt"));
			select(directoryLog);
			var unreadableResult = io.test(null, false);
			require(!unreadableResult.isPass() && unreadableResult.getNotes().contains("readable text file"), "Directory log lacks useful diagnosis");

			Path ioOnly = Files.createDirectory(root.resolve("io-only"));
			Files.writeString(ioOnly.resolve("CLIInputOutput.txt"), LOG);
			Path otherRun = Files.createDirectory(ioOnly.resolve("other-run"));
			Files.writeString(otherRun.resolve("CLITrace.txt"), "");
			select(ioOnly);
			require(!tracePasses(), "Trace borrowed from another run");
			require(io.test(null, false).isPass(), "Valid I/O blocked by missing trace");
			require(io.getInteractionsFile().equals(ioOnly.resolve("CLIInputOutput.txt")), "Wrong run selected");

			Path nested = Files.createDirectory(root.resolve("nested"));
			Path submission = Files.createDirectory(nested.resolve("submission"));
			Files.writeString(submission.resolve("CLITrace.txt"), "");
			Files.writeString(submission.resolve("CLIInputOutput.txt"), LOG);
			select(nested);
			require(tracePasses() && io.test(null, false).isPass(), "Nested file set not found");
			require(FilesProvidedSupport.resolvedDirectory().equals(submission), "Prerequisites chose different roots");

			Path split = Files.createDirectory(root.resolve("split"));
			Files.writeString(Files.createDirectory(split.resolve("one")).resolve("CLITrace.txt"), "");
			Files.writeString(Files.createDirectory(split.resolve("two")).resolve("CLIInputOutput.txt"), LOG);
			select(split);
			require(!tracePasses() && !io.test(null, false).isPass(), "Ambiguous split runs combined");
			System.out.println("FilesProvidedSuiteTest passed");
		} finally {
			A1ChecksConfiguration.configureProperties();
			select(Path.of("."));
		}
	}

	private static final class ExpectedCheckProbe extends AdaptiveGeminiExpectedPromptsInProviderRequests {
		boolean requiresInteractions() { return Arrays.asList(additionalPrecedingTests()).contains(
				gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided.class); }
	}
	private static void select(Path directory) { FilesProvidedSupport.setDirectory(directory.toString()); }
	private static boolean tracePasses() throws Exception { return new AdaptiveTraceFilesProvided().test(null, false).isPass(); }
	private static void require(boolean condition, String message) { if (!condition) throw new AssertionError(message); }
}
