package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import java.util.List;
import java.util.Map;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.BridgeDemoTraceFilesProcessor;
import eduAI.trace.processors.TraceCheckResult;
import gradingTools.comp690f26.assignment1.AbstractTraceProcessorCheck;

public abstract class AbstractTraceEventTraced
		extends AbstractTraceProcessorCheck {
	protected static final String GEMINI = "Gemini";
	protected static final String OLLAMA = "Ollama";
	protected static final List<StreamingMode> BOTH_MODES =
			List.of(StreamingMode.NON_STREAMING, StreamingMode.STREAMING);
	protected static final List<StreamingMode> STREAMING_ONLY =
			List.of(StreamingMode.STREAMING);
	protected static final List<StreamingMode> NON_STREAMING_ONLY =
			List.of(StreamingMode.NON_STREAMING);

	protected abstract LibTraceEvent traceEvent();

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
				+ " trace event was not found. See trace output for details.";
	}

	protected Map<String, List<StreamingMode>> expectedProviderModes() {
		return Map.of(GEMINI, BOTH_MODES, OLLAMA, BOTH_MODES);
	}

	@Override
	protected TraceCheckResult check(
			BridgeDemoTraceFilesProcessor aProcessor) {
		return aProcessor.checkTraceEventExists(
				traceEvent().name().toLowerCase(),
				expectedProviderModes());
	}
}
