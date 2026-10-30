package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;
import java.util.List;
import java.util.Map;


public abstract class AbstractTraceEventTraced
		extends gradingTools.comp690f26.common.AbstractSessionTraceEventTraced {
	protected static final String GEMINI = "Gemini";
	protected static final String OLLAMA = "Ollama";
	protected static final List<StreamingMode> BOTH_MODES =
			List.of(StreamingMode.NON_STREAMING, StreamingMode.STREAMING);
	protected static final List<StreamingMode> STREAMING_ONLY =
			List.of(StreamingMode.STREAMING);
	protected static final List<StreamingMode> NON_STREAMING_ONLY =
			List.of(StreamingMode.NON_STREAMING);

	protected abstract LibTraceEvent traceEvent();
    @Override protected String eventName() { return traceEvent().name().toLowerCase(java.util.Locale.ROOT); }

	@Override
	protected Class<?>[] additionalPrecedingTests() {
		return new Class<?>[]{TraceProviderNamesConsistent.class};
	}

	@Override
	protected String failureGuidance() {
		return TraceIntegrityExplanations.forEvent(traceEvent());
	}

	@Override
	protected String shortFailureMessage(TraceCheckResult aResult) {
		return "Required "
				+ traceEvent().name().toLowerCase()
				+ " trace is missing or has invalid arguments. See trace output for details.";
	}

	protected Map<String, List<StreamingMode>> expectedProviderModes() {
		return Map.of(GEMINI, BOTH_MODES, OLLAMA, BOTH_MODES);
	}

	@Override
	protected TraceCheckResult check(
			TraceFilesProcessor aProcessor) {
        var projectClasses=gradingTools.comp690f26.common.EventProjectClasses.check(aProcessor,java.util.Set.of(eventName()));
        if(!projectClasses.passed()) return projectClasses;
		var required = gradingTools.comp690f26.files.FilesProvidedSupport.configuration()
				.checks().traceEventEvidence().select(aProcessor, expectedProviderModes());
		if (required.isEmpty()) {
			String message = "No requests exercise the provider/modes required for " + traceEvent();
			return new TraceCheckResult(List.of(), List.of(message), List.of(message));
		}
		return checkEvent(aProcessor, required);
	}
    protected TraceCheckResult checkEvent(TraceFilesProcessor processor, Map<String, List<StreamingMode>> required) {
        return processor.checkTraceEventExists(eventName(), required);
    }
}
