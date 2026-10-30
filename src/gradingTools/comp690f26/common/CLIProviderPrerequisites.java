package gradingTools.comp690f26.common;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.*;
import java.util.*;

/** Explicit A1 check selection from request providers and modes; unknown providers are errors. */
public final class CLIProviderPrerequisites {
    public enum Evidence { RESPONSE, PARAMETERS, CONTEXT, IMAGES, ACCUMULATION, CALLBACK }
    private CLIProviderPrerequisites() { }
    public static Class<?>[] checks(TraceFilesProcessor processor,Evidence... evidence) {
        Set<Class<?>> checks=new LinkedHashSet<>();
        for(var record:processor.getTraceRecords().values()) {
            if(!LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT).equals(record.getTraceLine().getEventName())) continue;
            String provider=TraceProviderNames.canonical(record.getId().provider());
            for(var required:evidence) {
                if((required==Evidence.ACCUMULATION || required==Evidence.CALLBACK) && record.getId().mode()!=StreamingMode.STREAMING) continue;
                checks.add(check(provider,required));
            }
        }
        return checks.toArray(Class<?>[]::new);
    }
    private static Class<?> check(String provider,Evidence evidence) {
        return switch(provider) {
            case TraceProviderNames.GEMINI -> switch(evidence) {
                case IMAGES -> gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiImageMatchesGenericImage.class;
                case CONTEXT -> gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiTextualContextWindowMatchesGenericContextWindow.class;
                case RESPONSE -> gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiResponseMatchesGenericResponse.class;
                case PARAMETERS -> gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiParametersMatchGenericParameters.class;
                case ACCUMULATION -> gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiStreamingChunksAccumulatedCorrectly.class;
                case CALLBACK -> gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiPartialResponseCallbackInvoked.class;
            };
            case TraceProviderNames.OLLAMA -> switch(evidence) {
                case IMAGES -> gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaImageMatchesGenericImage.class;
                case CONTEXT -> gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaTextualContextWindowMatchesGenericContextWindow.class;
                case RESPONSE -> gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaResponseMatchesGenericResponse.class;
                case PARAMETERS -> gradingTools.comp690f26.assignment1.requestTranslationSuite.OllamaParametersMatchGenericParameters.class;
                case ACCUMULATION -> gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaStreamingChunksAccumulatedCorrectly.class;
                case CALLBACK -> gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaPartialResponseCallbackInvoked.class;
            };
            default -> throw new IllegalArgumentException("No CLI evidence checks configured for provider "+provider);
        };
    }
}
