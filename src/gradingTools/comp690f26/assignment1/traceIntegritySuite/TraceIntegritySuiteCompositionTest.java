package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;

import org.junit.runners.Suite;

import eduAI.trace.processors.EssentialTraceEvents;
import gradingTools.comp690f26.assignment1.projectClassesSuite.AllTracedClassesExistInProject;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.CompleteResponseCallbacksTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.MessageMergerFactoriesFetchedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksAccumulatedTraced;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.extra.StreamingChunksMergedTraced;

public final class TraceIntegritySuiteCompositionTest {
	private static final Map<String, Class<?>> EVENT_CHECKS = Map.ofEntries(
			Map.entry("request_sent", RequestsTraced.class),
			Map.entry("server_handle_factory_fetched", ServerHandleFactoriesFetchedTraced.class),
			Map.entry("server_handle_fetched", ServerHandlesFetchedTraced.class),
			Map.entry("message_translated", MessageTranslationsTraced.class),
			Map.entry("response_translated", ResponsesTranslatedTraced.class),
			Map.entry("parameter_translated", ParametersTranslatedTraced.class),
			Map.entry("metadata_translated", MetadataTranslatedTraced.class),
			Map.entry("provider_streaming_chunk_received", ProviderStreamingChunksReceivedTraced.class),
			Map.entry("message_merger_factory_fetched", MessageMergerFactoriesFetchedTraced.class),
			Map.entry("streaming_chunk_accumulated", StreamingChunksAccumulatedTraced.class),
			Map.entry("streaming_chunks_merged", StreamingChunksMergedTraced.class),
			Map.entry("partial_response_callback_invoked", PartialResponseCallbacksTraced.class),
			Map.entry("complete_response_callback_invoked", CompleteResponseCallbacksTraced.class));

	private TraceIntegritySuiteCompositionTest() {
	}

	public static void assertSuiteContainsExactlyRequiredChecks() {
		Set<String> requiredEvents = EssentialTraceEvents.all();
		if (!EVENT_CHECKS.keySet().equals(requiredEvents)) {
			Set<String> unmappedEvents = new LinkedHashSet<>(requiredEvents);
			unmappedEvents.removeAll(EVENT_CHECKS.keySet());
			Set<String> unnecessaryMappings =
					new LinkedHashSet<>(EVENT_CHECKS.keySet());
			unnecessaryMappings.removeAll(requiredEvents);
			throw new AssertionError(
					"Trace-event check mapping is incorrect. unmappedEvents="
							+ unmappedEvents
							+ " unnecessaryMappings="
							+ unnecessaryMappings);
		}
		Set<Class<?>> requiredChecks = new LinkedHashSet<>(EVENT_CHECKS.values());
		requiredChecks.add(TraceProviderNamesConsistent.class);
		requiredChecks.add(AllTracedClassesExistInProject.class);
		Suite.SuiteClasses suiteClasses =
				A1TraceIntegritySuite.class.getAnnotation(
						Suite.SuiteClasses.class);
		if (suiteClasses == null) {
			throw new AssertionError(
					"A1TraceIntegritySuite has no SuiteClasses annotation");
		}
		Set<Class<?>> actualChecks =
				new LinkedHashSet<>(Set.of(suiteClasses.value()));
		Set<Class<?>> missingChecks = new LinkedHashSet<>(requiredChecks);
		missingChecks.removeAll(actualChecks);
		Set<Class<?>> unexpectedChecks = new LinkedHashSet<>(actualChecks);
		unexpectedChecks.removeAll(requiredChecks);
		if (!missingChecks.isEmpty() || !unexpectedChecks.isEmpty()) {
			throw new AssertionError(
					"A1TraceIntegritySuite composition is incorrect. missing="
							+ simpleNames(missingChecks)
							+ " unexpected="
							+ simpleNames(unexpectedChecks));
		}
	}

	private static Set<String> simpleNames(Set<Class<?>> classes) {
		Set<String> result = new LinkedHashSet<>();
		for (Class<?> type : classes) {
			result.add(type.getSimpleName());
		}
		return result;
	}

	public static void main(String[] args) {
		assertSuiteContainsExactlyRequiredChecks();
		System.out.println(
				"A1TraceIntegritySuite contains exactly the required checks");
	}
}
