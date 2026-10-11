package gradingTools.comp690f26.assignment1.separatedClassesSuite;

import java.util.List;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.TraceFilesProvided;
import util.trace.Tracer;

public abstract class AbstractClassIsSeparated
		extends PassFailJUnitTestCase {
	protected abstract String getTag();

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		TraceFilesProcessor traceFilesProcessor =
				((TraceFilesProvided) getFirstPrecedingTestInstance())
						.getTraceFilesProcessor();
		String tag = getTag();
		for (String className : traceFilesProcessor.classesForTag(tag)) {
			TraceCheckResult traceCheckResult =
					traceFilesProcessor.checkClassHasMultipleTags(className);
			for (String message : traceCheckResult.level3Messages()) {
				System.out.println(message);
			}

			List<String> tags = traceFilesProcessor.tagsForClass(className);
			if (tags.size() > 1) {
				String message =
						"Expected class to have only one role in trace: "
								+ tag
								+ " class="
								+ className
								+ " roles="
								+ tags;
				Tracer.error(message);
				return fail(message);
			}
		}
		return pass();
	}
}
