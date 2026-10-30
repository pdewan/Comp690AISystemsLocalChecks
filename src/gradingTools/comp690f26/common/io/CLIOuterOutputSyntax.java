package gradingTools.comp690f26.common.io;

import eduAI.trace.processors.cli.CLIMixedIO;
import eduAI.trace.processors.cli.CLIMixedIOProcessingResult;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.files.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;
import util.annotations.Explanation;

/** Parses outer framing and input grammar. Matching echo/start/end numbers pass;
 * malformed markers, duplicate/nonincreasing numbers and empty sessions fail.
 * This supplies parsed evidence, but does not inspect command-specific bodies. */
@Explanation("Checks outer output framing and parses echoed inputs; malformed markers or empty sessions fail.")
public class CLIOuterOutputSyntax extends PassFailJUnitTestCase {
    @Override protected Class[] precedingTests() { return new Class[]{CLIInputOutputFileProvided.class}; }
	private CLIMixedIOProcessingResult processingResult;
	private Path interactionsFile;

	@Override
	public TestCaseResult test(Project project, boolean interactive)
			throws NotAutomatableException, NotGradableException {
		processingResult = null;
		interactionsFile = null;
        interactionsFile=((CLIInputOutputFileProvided)getFirstPrecedingTestInstance()).getInteractionsFile();
        var source=FilesProvidedSupport.configuration().interactions().orElseThrow(
            ()->new IllegalStateException("CLI interaction source must be configured"));
        processingResult=source.processorFactory().get().process(interactionsFile);
		if (!processingResult.succeeded()) {
			return fail(processingResult.diagnostics().stream()
					.map(d -> d.lineNumber() > 0
							? interactionsFile.toAbsolutePath().normalize() + ": line " + d.lineNumber() + ": " + d.message()
							: d.message())
					.collect(Collectors.joining("\n")));
		}
		if (processingResult.interaction().orElseThrow().entries().isEmpty()) {
			processingResult = null;
			return fail("No CLI interactions found in " + interactionsFile.toAbsolutePath().normalize()
					+ ". Save the console transcript containing echoed commands, prompts, and outputs from the trace's run.");
		}
		return pass();
	}

	/** Empty before parsing or after a parsing failure. */
	public Optional<CLIMixedIO> getInteractions() {
		return processingResult == null ? Optional.empty() : processingResult.interaction();
	}
	public Path getInteractionsFile() { return interactionsFile; }
	public CLIMixedIOProcessingResult getProcessingResult() { return processingResult; }
}
