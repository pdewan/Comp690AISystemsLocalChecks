package gradingTools.comp690f26.files;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import util.annotations.Explanation;
import util.annotations.MaxValue;
import util.trace.Tracer;

/** Availability only: readable malformed traces pass this check. Consumers request
 * parsed records lazily; integrity and semantics remain separately scored. */
@Explanation("Requires readable trace files; parsing is deferred until a dependent check requests the processor.")
@MaxValue(0)
public class AdaptiveTraceFilesProvided extends PassFailJUnitTestCase {

	private TraceFilesProcessor traceFilesProcessor;
    private boolean processed;
    private boolean filesProvided;
	 
	@Override
	public TestCaseResult test(Project aProject, boolean arg1) throws NotAutomatableException, NotGradableException {
		traceFilesProcessor = null;
        processed = false;
        filesProvided = false;
		try {
			Path directory = FilesProvidedSupport.resolvedDirectory();
			traceFilesProcessor = FilesProvidedSupport.configuration().traceProcessorFactory().apply(directory);
		} catch (IllegalStateException | IllegalArgumentException | UncheckedIOException exception) {
			return fail("Cannot locate trace files: " + exception.getMessage());
		}
		TraceCheckResult traceCheckResult = traceFilesProcessor.checkTraceFilesExist();
		for (String message : traceCheckResult.level3Messages()) {
		   System.out.println(message);
		}
		
		if (!traceCheckResult.passed()) {
			String aMessage = "Trace files not provided: "
					+ String.join("; ", traceCheckResult.level3ErrorMessages());
			Tracer.error(aMessage);
			return fail(aMessage);
		}

        filesProvided = true;
		return pass();
	}
	public TraceFilesProcessor getTraceFilesProcessor() {
        if(!filesProvided)
            throw new IllegalStateException("Readable trace files must be provided before processing");
        if(!processed) { traceFilesProcessor.processTraceFiles(); processed=true; }
		return traceFilesProcessor;
	}
}
