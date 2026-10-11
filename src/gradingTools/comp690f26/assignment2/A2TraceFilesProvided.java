package gradingTools.comp690f26.assignment2;
import java.util.List;
import java.util.stream.Collectors;

import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessorError;
import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import util.trace.Tracer;
import util.annotations.Explanation;
import util.annotations.MaxValue;
@Explanation("Requires four readable demo logs; supplies the shared processor used by dependent tests.")
@MaxValue(0)
public class A2TraceFilesProvided extends PassFailJUnitTestCase {

	BridgeDemoTraceFilesProcessor bridgeDemoProcessor;
	static String traceDirectory = ".";
	 
	public static void setTraceDirectory(String aTraceDirectory) {
		traceDirectory = aTraceDirectory;
	}
	
	@Override
	public TestCaseResult test(Project aProject, boolean arg1) throws NotAutomatableException, NotGradableException {
		bridgeDemoProcessor = new BridgeDemoTraceFilesProcessor();
		bridgeDemoProcessor.setTraceDirectory(traceDirectory);
		TraceCheckResult traceCheckResult = bridgeDemoProcessor.checkTraceFilesExist();
		
		List<TraceFilesProcessorError> errors = traceCheckResult.errors();
		if (errors == null) {
			String aMessage = "Internal error in checking trace files existence: error == null; Please contact instructor.";
			Tracer.error(aMessage);
			return fail(aMessage);
		}
		
			
		for (String message : traceCheckResult.level3Messages()) {
		   System.out.println(message);
		}
		
		if (!traceCheckResult.passed()) {
//			for (String message : traceCheckResult.level3ErrorMessages()) {
//				System.out.println(message);
//			}
//			String aMessage = traceFilesNotProvidedMessage(traceCheckResult);
			String aMessage = "Trace files not provided: " + errors;
			Tracer.error(aMessage);
			return fail(aMessage);
		}
		return pass();
	}
	public BridgeDemoTraceFilesProcessor getBridgeDemoProcessor() {
		return bridgeDemoProcessor;
	}
	
	
	
	
	
	public static String getTraceDirectory() {
		return traceDirectory;
	}

	private String traceFilesNotProvidedMessage(TraceCheckResult aResult) {
		if (!aResult.errors().isEmpty()) {
			return "Trace files not provided: " + aResult.errors();
		}
		return "Trace files not provided: "
				+ aResult.level3ErrorMessages()
						.stream()
						.map(this::messageWithoutTraceLevelPrefix)
						.collect(Collectors.toList());
	}

	private String messageWithoutTraceLevelPrefix(String aMessage) {
		if (aMessage == null) {
			return "";
		}
		return aMessage.replaceFirst("^[IWEU]\\*{3}\\s+", "");
	}

}
