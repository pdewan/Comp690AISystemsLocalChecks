package gradingTools.comp690f26.files._tests;

import grader.junit.AJUnitProjectRequirements;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import gradingTools.comp690f26.files.AdaptiveTraceFilesProvided;
import java.nio.file.Files;
import java.util.List;

/** Run once per assignment in a fresh JVM. The grader instantiates the suite,
 * without invoking main or JUnit's BeforeClass hook. Configuration must then
 * identify the selected assignment. Merely reading a class literal is not enough. */
public final class SuiteInitializationTest {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Expected suite class and whether CLI I/O is required");
        Class<?> suite = Class.forName(args[0], false, SuiteInitializationTest.class.getClassLoader());
        new AJUnitProjectRequirements().addJUnitTestSuite(suite);
        boolean interactions = FilesProvidedSupport.configuration().interactions().isPresent();
        if (interactions != Boolean.parseBoolean(args[1]))
            throw new AssertionError("Suite initialized the wrong assignment");
        var upload = Files.createTempDirectory("suite-initialization-");
        var project = Files.createDirectories(upload.resolve("AIDemo"));
        var names = interactions ? List.of("CLITrace.txt") : List.of(
                "TraceGeminiBridgeStreamingDemo.txt", "TraceGeminiBridgeNonStreamingDemo.txt",
                "TraceOllamaBridgeStreamingDemo.txt", "TraceOllamaBridgeNonStreamingDemo.txt");
        for (String name : names) Files.writeString(project.resolve(name), "");
        FilesProvidedSupport.setDirectory(upload.toString());
        var result = new AdaptiveTraceFilesProvided().test(null, false);
        if (!result.isPass()) throw new AssertionError(result.getNotes());
        System.out.println("Suite initialization passed: " + suite.getName());
        System.exit(0);
    }
}

