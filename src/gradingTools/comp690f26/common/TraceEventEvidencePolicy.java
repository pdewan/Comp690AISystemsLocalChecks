package gradingTools.comp690f26.common;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.lib.StreamingMode;
import eduAI.trace.processors.TraceFilesProcessor;
import java.util.*;


/** A1 requires its prescribed demo modes; a CLI session requires the modes it used. */
public enum TraceEventEvidencePolicy {
    PRESCRIBED_MODES, OBSERVED_REQUEST_MODES;

    public Map<String, List<StreamingMode>> select(TraceFilesProcessor processor,
            Map<String, List<StreamingMode>> prescribed) {
        if (this == PRESCRIBED_MODES && !processor.isEvidenceSelection()) return prescribed;
        Map<String, List<StreamingMode>> selected = new LinkedHashMap<>();
        for (var record : processor.getTraceRecords().values()) {
            if (!LibTraceEvent.REQUEST_SENT.name().toLowerCase(Locale.ROOT)
                    .equals(record.getTraceLine().getEventName())) continue;
            String provider = record.getId().provider();
            var modes = prescribed.get(provider);
            if (modes != null && modes.contains(record.getId().mode())) {
                var observed = selected.computeIfAbsent(provider, key -> new ArrayList<>());
                if (!observed.contains(record.getId().mode())) observed.add(record.getId().mode());
            }
        }
        return selected;
    }
}
