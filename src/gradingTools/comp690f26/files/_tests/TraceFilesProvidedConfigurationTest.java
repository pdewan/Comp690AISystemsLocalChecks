package gradingTools.comp690f26.files._tests;

import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/** Exercises the actual shared prerequisite, including switching suites in one JVM. */
public final class TraceFilesProvidedConfigurationTest {
	public static void main(String[] args) throws Throwable {
		var unconfigured = new AdaptiveTraceFilesProvided().test(null, false);
		if (unconfigured.isPass() || !unconfigured.getNotes().contains("not configured"))
			throw new AssertionError("Missing configuration silently selected an assignment");
		var unconfiguredIO = new gradingTools.comp690f26.common.io.CLIOuterOutputSyntax().test(null, false);
		if (unconfiguredIO.isPass() || !unconfiguredIO.getNotes().contains("not configured"))
			throw new AssertionError("Missing configuration silently skipped CLI interactions");
		Path root = Files.createTempDirectory("assignment-trace-files-");
		Path a1 = Files.createDirectory(root.resolve("a1"));
		for (String name : List.of("TraceGeminiBridgeNonStreamingDemo.txt",
				"TraceGeminiBridgeStreamingDemo.txt", "TraceOllamaNonStreamingBridgeDemo.txt",
				"TraceOllamaStreamingBridgeDemo.txt")) Files.writeString(a1.resolve(name), "");
		Path a2 = Files.createDirectory(root.resolve("a2"));
		Files.writeString(a2.resolve("CLITrace.txt"), "");
		Path missing = Files.createDirectory(root.resolve("missing"));
		try {
			// Each assignment must configure the shared prerequisite explicitly.
			configureSuite(F26Assignment1Suite.class);
			verify(a1, true);
			configureSuite(F26Assignment2Suite.class);
			verify(a2, true);
			verify(a1, false);
			verify(missing, false);
			Path compressed = Files.createDirectory(root.resolve("compressed"));
			try (var output = new GZIPOutputStream(Files.newOutputStream(compressed.resolve("CLITrace.txt.gz")))) {
				output.write("fixture".getBytes(java.nio.charset.StandardCharsets.UTF_8));
			}
			verify(compressed, true);
			Path ambiguous = Files.createDirectory(root.resolve("ambiguous"));
			for (String child : List.of("one", "two")) {
				Files.writeString(Files.createDirectory(ambiguous.resolve(child)).resolve("CLITrace.txt"), "");
			}
			verify(ambiguous, false);
			configureSuite(F26Assignment1Suite.class);
			verify(a1, true);
			verify(a2, false);
			F26Assignment1Suite.setTraceDirectory(a1.toString());
			F26Assignment2Suite.setTraceDirectory(a2.toString());
			configureSuite(F26Assignment1Suite.class);
			if (!FilesProvidedSupport.resolvedDirectory().equals(a1))
				throw new AssertionError("A1 lost its selected directory when switching suites");
			configureSuite(F26Assignment2Suite.class);
			if (!FilesProvidedSupport.resolvedDirectory().equals(a2))
				throw new AssertionError("A2 lost its selected directory when switching suites");
			for (String invalid : new String[]{null, "", "   "}) {
				try {
					FilesProvidedSupport.setDirectory(invalid);
					throw new AssertionError("Invalid directory silently substituted");
				} catch (IllegalArgumentException expected) { }
			}
			System.out.println("TraceFilesProvidedConfigurationTest passed");
		} finally {
			F26Assignment1Suite.configureProperties();
			FilesProvidedSupport.setDirectory(".");
		}
	}

	private static void configureSuite(Class<?> suite) throws Throwable {
		var hooks = new org.junit.runners.model.TestClass(suite)
				.getAnnotatedMethods(org.junit.BeforeClass.class);
		if (hooks.size() != 1) throw new AssertionError("Expected one configuration hook for " + suite);
		hooks.get(0).invokeExplosively(null);
	}

	private static void verify(Path directory, boolean expected) throws Exception {
		FilesProvidedSupport.setDirectory(directory.toString());
		AdaptiveTraceFilesProvided prerequisite = new AdaptiveTraceFilesProvided();
		if (prerequisite.test(null, false).isPass() != expected) {
			throw new AssertionError("Unexpected prerequisite result for " + directory);
		}
		if (expected && prerequisite.getTraceFilesProcessor() == null) {
			throw new AssertionError("Successful prerequisite did not expose its processor");
		}
	}
}
