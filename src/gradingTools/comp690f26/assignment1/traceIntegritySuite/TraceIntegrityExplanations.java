package gradingTools.comp690f26.assignment1.traceIntegritySuite;

import eduAI.trace.lib.LibTraceEvent;

public final class TraceIntegrityExplanations {
	public static final String PROVIDER_NAMES_CONSISTENT_EXPLANATION =
			"After factory-fetch and project checks, rejects conflicting provider identities; gates trace checks.";
	public static final String REQUEST_SENT_EXPLANATION =
			"Requires request dumps for both modes before request-value and history checks.";
	public static final String SERVER_HANDLE_FACTORY_FETCHED_EXPLANATION =
			"Requires factory/registry lookup evidence to establish provider identity before other trace checks.";
	public static final String SERVER_HANDLE_FETCHED_EXPLANATION =
			"Requires factory-to-handle evidence before inspecting the adapter's native-client field types.";
	public static final String MESSAGE_TRANSLATED_EXPLANATION =
			"Requires prompt-adapter evidence; request values are checked separately.";
	public static final String RESPONSE_TRANSLATED_EXPLANATION =
			"Requires native/generic response pairs before response comparisons and text-history checks.";
	public static final String PARAMETER_TRANSLATED_EXPLANATION =
			"Requires key, value, adapter, and native-target evidence before parameter comparisons.";
	public static final String METADATA_TRANSLATED_EXPLANATION =
			"Requires key, value, adapter, and native-source evidence before metadata comparisons.";
	public static final String MESSAGE_MERGER_FACTORY_FETCHED_EXPLANATION =
			"Requires merger-factory lookup evidence; semantic accumulation is checked separately.";
	public static final String STREAMING_CHUNK_ACCUMULATED_EXPLANATION =
			"Requires running-result evidence before accumulation and next-context checks.";
	public static final String STREAMING_CHUNKS_MERGED_EXPLANATION =
			"Requires the final merged value before accumulation and next-context checks.";
	public static final String PARTIAL_RESPONSE_CALLBACK_INVOKED_EXPLANATION =
			"Requires callback arguments before checking that partial delivery contains single chunks.";
	public static final String COMPLETE_RESPONSE_CALLBACK_INVOKED_EXPLANATION =
			"Requires final-callback evidence before comparing its argument with the merged response.";
	public static final String METADATA_PROPERTY_STORE_CREATED_EXPLANATION =
			"Supplemental store-creation evidence; not a prerequisite of the active metadata comparisons.";
	public static final String STREAM_HANDLER_REGISTERED_EXPLANATION =
			"Supplemental handler-registration evidence; active callback checks use invocation evidence.";
	public static final String MESSAGE_MERGER_CREATED_EXPLANATION =
			"Supplemental merger-creation evidence; active accumulation checks use chunk/result evidence.";
	public static final String PROVIDER_STREAMING_CHUNK_RECEIVED_EXPLANATION =
			"Requires native chunk evidence before validating partial-callback delivery.";
	public static final String PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION =
			"Supplemental generic-window evidence; active request comparisons use request_sent dumps.";
	public static final String PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED_EXPLANATION =
			"Supplemental native-window evidence; active request comparisons use request_sent dumps.";

	public static final String SUITE =
			"Validates required event evidence, not behavior; gates comparisons after project-class checks.";

	public static final String PROVIDER_NAMES_CONSISTENT = """
			Provider identity comes only from server_handle_factory_fetched, where it is
			the actual registry lookup key. The same factory identity must not be associated
			with different provider keys. Filenames and directories do not identify providers.
			""";

	public static final String REQUEST_SENT = """
			Missing request_sent trace. Call LibTraceActions.traceRequestSent from the
			provider server-handle adapter immediately before it invokes the native request.
			Pass the source class, StreamingMode, generic parameter store, generic
			context window, native configuration, and native context window. Example:
			## lib {request_sent} [...] <mode=\"NON_STREAMING\"
			 providerIndependentParameterStore=\"...\" providerIndependentContextWindow=\"...\"
			 providerDependentConfiguration=\"...\" providerDependentContextWindow=\"...\"> ##
			""";

	public static final String SERVER_HANDLE_FACTORY_FETCHED = """
			Missing server_handle_factory_fetched trace. Call
			LibTraceActions.traceServerHandleFactoryFetched where the provider's server-handle
			factory is retrieved from its registry. Pass the source class, provider name,
			registry object, and fetched factory object. Example:
			## lib {server_handle_factory_fetched} [...] <provider=\"Gemini\"
			 serverHandleFactoryRegistry=\"...\" serverHandleFactory=\"...\"> ##
			""";

	public static final String SERVER_HANDLE_FETCHED = """
			Missing server_handle_fetched trace. Call LibTraceActions.traceServerHandleFetched
			at the factory invocation immediately after obtaining the server handle.
			Pass the source class, exact fetched factory object, and returned handle object.
			Example:
			## lib {server_handle_fetched} [...] <serverHandleFactoryIdentity=\"...\"
			 serverHandleClass=\"student.impl.GeminiClient\" serverHandleIdentity=\"...\"> ##
			""";

	public static final String MESSAGE_TRANSLATED = """
			Missing message_translated trace. Call LibTraceActions.traceMessageTranslated in
			the provider prompt-message adapter after translating each generic message. Pass
			the adapter class, generic message, and native message. Example:
			## lib {message_translated} [...]
			 providerIndependentMessage=\"...\" providerDependentMessage=\"...\"> ##
			""";

	public static final String RESPONSE_TRANSLATED = """
			Missing response_translated trace. Call LibTraceActions.traceResponseTranslated
			in the provider response-message adapter after translating the native response.
			Pass the adapter class, native response, and translated generic
			response. Example:
			## lib {response_translated} [...]
			 providerDependentResponse=\"...\" providerIndependentResponse=\"...\"> ##
			""";

	public static final String PARAMETER_TRANSLATED = """
			Missing parameter_translated trace. Call LibTraceActions.traceParameterTranslated
			in the common parameter-handler path after each generic parameter is applied to the
			native configuration. Pass the source class, property name and value, parameter
			handler, and provider-dependent target. Example:
			## lib {parameter_translated} [...] <propertyName=\"temperature\"
			 propertyValue=\"0.4\" parameterHandler=\"...\" providerDependentTarget=\"...\"> ##
			""";

	public static final String METADATA_TRANSLATED = """
			Missing metadata_translated trace. Call LibTraceActions.traceMetadataTranslated
			in the common metadata-handler path after each native metadata value is translated.
			Pass the source class, property name and value, metadata handler, and provider-
			dependent source. Example:
			## lib {metadata_translated} [...] <propertyName=\"inputTokens\"
			 propertyValue=\"1185\" metadataHandler=\"...\" providerDependentSource=\"...\"> ##
			""";

	public static final String MESSAGE_MERGER_FACTORY_FETCHED = """
			Missing message_merger_factory_fetched trace. Call
			LibTraceActions.traceMessageMergerFactoryFetched where the streaming merger factory
			is retrieved. Pass the source class, merger-factory registry, and fetched
			factory. Example:
			## lib {message_merger_factory_fetched} [...]
			 streamChunkMergerFactoryRegistry=\"...\" streamChunkMergerFactory=\"...\"> ##
			""";

	public static final String STREAMING_CHUNK_ACCUMULATED = """
			Missing streaming_chunk_accumulated trace. Call
			LibTraceActions.traceStreamingChunkAccumulated inside the merger after adding every
			chunk. Pass the merger class, merger object, current chunk message, and accumulated
			message. Example:
			## lib {streaming_chunk_accumulated} [...] <messageMerger=\"...\"
			 streamingChunk=\"...\" accumulatedMessage=\"...\"> ##
			""";

	public static final String STREAMING_CHUNKS_MERGED = """
			Missing streaming_chunks_merged trace. Call
			LibTraceActions.traceStreamingChunksMerged inside the merger when it returns the
			completed message. Pass the merger class, merger object, and merged message. Example:
			## lib {streaming_chunks_merged} [...] <messageMerger=\"...\"
			 mergedMessage=\"...\"> ##
			""";

	public static final String PARTIAL_RESPONSE_CALLBACK_INVOKED = """
			Missing partial_response_callback_invoked trace. The provider streaming adapter
			must call LibTraceActions.tracePartialResponseCallbackInvoked immediately before
			invoking the application's partial-response callback. Pass the adapter class,
			callback object, and partial generic response. Example:
			## lib {partial_response_callback_invoked} [...] <streamingCallbackClass=\"...\"
			 streamingCallback=\"...\" callbackArgument=\"...\"> ##
			""";

	public static final String COMPLETE_RESPONSE_CALLBACK_INVOKED = """
			Missing complete_response_callback_invoked trace. The provider streaming adapter
			must call LibTraceActions.traceCompleteResponseCallbackInvoked immediately before
			invoking the application's completion callback. Pass the adapter class, callback
			object, and merged generic response. Example:
			## lib {complete_response_callback_invoked} [...] <streamingCallbackClass=\"...\"
			 streamingCallback=\"...\" callbackArgument=\"...\"> ##
			""";

	public static final String METADATA_PROPERTY_STORE_CREATED = """
			Missing metadata_property_store_created trace. Call
			LibTraceActions.traceMetadataPropertyStoreCreated in the response adapter when it
			creates the generic metadata store. Pass the adapter class and new store. Example:
			## lib {metadata_property_store_created} [...] <metadataPropertyStore=\"...\"> ##
			""";

	public static final String STREAM_HANDLER_REGISTERED = """
			Missing stream_handler_registered trace. Call
			LibTraceActions.traceStreamHandlerRegistered where the streaming callback is
			registered. Pass the registering source class and callback object. Example:
			## lib {stream_handler_registered} [...] <streamingHandlerClass=\"...\"
			 streamingHandler=\"...\"> ##
			""";

	public static final String MESSAGE_MERGER_CREATED = """
			Missing message_merger_created trace. Call LibTraceActions.traceMessageMergerCreated
			immediately after the merger factory creates a merger. Pass the factory source class
			and created merger object. Example:
			## lib {message_merger_created} [...] <messageMergerClass=\"...\"
			 messageMerger=\"...\"> ##
			""";

	public static final String PROVIDER_STREAMING_CHUNK_RECEIVED = """
			Missing provider_streaming_chunk_received trace. Call
			LibTraceActions.traceProviderStreamingChunkReceived in the native streaming adapter
			as each native chunk arrives. Pass the adapter class and native chunk. Example:
			## lib {provider_streaming_chunk_received} [...] <providerStreamingChunk=\"...\"> ##
			""";

	public static final String PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED = """
			Missing provider_independent_context_window_prepared trace. Call
			LibTraceActions.traceProviderIndependentContextWindowPrepared after assembling the
			generic messages and before native translation. Pass the source class and generic
			context window. Example:
			## lib {provider_independent_context_window_prepared} [...] <contextWindow=\"...\"> ##
			""";

	public static final String PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED = """
			Missing provider_dependent_context_window_prepared trace. Call
			LibTraceActions.traceProviderDependentContextWindowPrepared after translating the
			generic context window. Pass the adapter class, provider, generic window, and native
			window. Example:
			## lib {provider_dependent_context_window_prepared} [...] <provider=\"Gemini\"
			 providerIndependentContextWindow=\"...\" providerContextWindow=\"...\"> ##
			""";

	private TraceIntegrityExplanations() {
	}

	public static String forEvent(LibTraceEvent anEvent) {
		return switch (anEvent) {
		case REQUEST_SENT -> REQUEST_SENT;
		case SERVER_HANDLE_FACTORY_FETCHED -> SERVER_HANDLE_FACTORY_FETCHED;
		case SERVER_HANDLE_FETCHED -> SERVER_HANDLE_FETCHED;
		case MESSAGE_TRANSLATED -> MESSAGE_TRANSLATED;
		case RESPONSE_TRANSLATED -> RESPONSE_TRANSLATED;
		case PARAMETER_TRANSLATED -> PARAMETER_TRANSLATED;
		case METADATA_TRANSLATED -> METADATA_TRANSLATED;
		case MESSAGE_MERGER_FACTORY_FETCHED -> MESSAGE_MERGER_FACTORY_FETCHED;
		case STREAMING_CHUNK_ACCUMULATED -> STREAMING_CHUNK_ACCUMULATED;
		case STREAMING_CHUNKS_MERGED -> STREAMING_CHUNKS_MERGED;
		case PARTIAL_RESPONSE_CALLBACK_INVOKED -> PARTIAL_RESPONSE_CALLBACK_INVOKED;
		case COMPLETE_RESPONSE_CALLBACK_INVOKED -> COMPLETE_RESPONSE_CALLBACK_INVOKED;
		case METADATA_PROPERTY_STORE_CREATED -> METADATA_PROPERTY_STORE_CREATED;
		case STREAM_HANDLER_REGISTERED -> STREAM_HANDLER_REGISTERED;
		case MESSAGE_MERGER_CREATED -> MESSAGE_MERGER_CREATED;
		case PROVIDER_STREAMING_CHUNK_RECEIVED -> PROVIDER_STREAMING_CHUNK_RECEIVED;
		case PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED ->
				PROVIDER_INDEPENDENT_CONTEXT_WINDOW_PREPARED;
		case PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED ->
				PROVIDER_DEPENDENT_CONTEXT_WINDOW_PREPARED;
		default -> "Missing required trace event: " + anEvent;
		};
	}
}
