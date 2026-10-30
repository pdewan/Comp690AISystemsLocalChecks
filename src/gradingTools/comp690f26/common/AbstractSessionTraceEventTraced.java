package gradingTools.comp690f26.common;

import eduAI.trace.processors.TraceCheckResult;
import eduAI.trace.processors.TraceFilesProcessor;
import java.util.List;

public abstract class AbstractSessionTraceEventTraced extends AbstractTraceProcessorCheck {
    protected abstract String eventName();
    @Override protected TraceCheckResult check(TraceFilesProcessor processor) {
        String event=eventName();
        var classes=EventProjectClasses.check(processor,java.util.Set.of(event));
        if(!classes.passed()) return classes;
        boolean present=processor.getTraceRecords().values().stream()
                .anyMatch(record->event.equals(record.getTraceLine().getEventName()));
        return present ? new TraceCheckResult(List.of(),List.of("Required trace present: "+event),List.of())
                : new TraceCheckResult(List.of(),List.of(),List.of("Missing required trace: "+event));
    }
    @Override protected String shortFailureMessage(TraceCheckResult result) {
        return String.join("\n",result.level3ErrorMessages());
    }
}
