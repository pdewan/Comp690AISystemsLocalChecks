package gradingTools.comp690f26.common;

import eduAI.trace.lib.LibTraceEvent;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.util.*;
import java.util.function.Predicate;


/** Fresh interpretation of explicitly selected evidence, without changing the submission processor. */
public final class TraceEvidenceView {
    private TraceEvidenceView() { }
    private static boolean event(TraceRecord record, LibTraceEvent event) {
        return event.name().toLowerCase(Locale.ROOT).equals(record.getTraceLine().getEventName());
    }
    private static boolean before(TraceRecord earlier, TraceRecord later) {
        return earlier.getTraceFileName().equals(later.getTraceFileName())
            && earlier.getTraceFileLineNumber() < later.getTraceFileLineNumber();
    }
    public static TraceFilesProcessor select(TraceFilesProcessor source, Predicate<TraceRecord> selected) {
        var all=source.getTraceRecords().values();
        var selectedRecords=all.stream().filter(selected).toList();
        var requests=selectedRecords.stream().filter(r->event(r,LibTraceEvent.REQUEST_SENT)).toList();
        var records=new LinkedHashSet<>(selectedRecords.stream()
            .filter(r -> !event(r,LibTraceEvent.SERVER_HANDLE_FACTORY_FETCHED)
                && (!event(r,LibTraceEvent.SERVER_HANDLE_FETCHED) || requests.stream().anyMatch(request ->
                    before(r,request) && r.getId().provider().equals(request.getId().provider())))).toList());
        // Identity is support for selected requests, not a whole-file gate.
        // Retain the real preceding handle and its factory lookup. Unused setup
        // stays independently graded by the full integrity checks.
        for(var request : requests) {
            var handle=all.stream().filter(r -> event(r,LibTraceEvent.SERVER_HANDLE_FETCHED)
                    && before(r,request) && r.getId().provider().equals(request.getId().provider()))
                .max(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber));
            if(handle.isEmpty()) continue; // No invented evidence: the prerequisite will reject its absence.
            records.add(handle.orElseThrow());
        }
        // One request can fetch handles for both message and parameter work.
        // Keep all selected preparation handles, not just the last one.
        for(var handle : List.copyOf(records)) {
            if(!event(handle,LibTraceEvent.SERVER_HANDLE_FETCHED)) continue;
            String identity=handle.getTraceLine().getAuxiliaryData().get(ProviderIdentityFields.FACTORY_IDENTITY);
            if(identity==null || identity.isBlank()) continue;
            all.stream().filter(r -> event(r,LibTraceEvent.SERVER_HANDLE_FACTORY_FETCHED)
                    && before(r,handle) && identity.equals(r.getTraceLine().getAuxiliaryData().get(ProviderIdentityFields.FACTORY_IDENTITY)))
                .max(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber)).ifPresent(records::add);
        }
        if(source.isEvidenceSelection() && records.size()==source.getTraceRecords().size()) return source;
        var result=FilesProvidedSupport.configuration().traceProcessorFactory().apply(FilesProvidedSupport.resolvedDirectory());
        result.processSelectedTraceLines(records);
        return result;
    }
}
