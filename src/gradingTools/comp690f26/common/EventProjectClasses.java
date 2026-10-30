package gradingTools.comp690f26.common;

import eduAI.trace.processors.*;
import grader.basics.project.CurrentProjectHolder;
import java.util.*;


/** Resolves only classes tagged by the consumed events. An unrelated tagged class
 * cannot veto event evidence; a missing participating class still does. */
public final class EventProjectClasses {
    private EventProjectClasses() { }
    public static TraceCheckResult check(TraceFilesProcessor processor, Set<String> events) {
        var project=CurrentProjectHolder.getOrCreateCurrentProject();
        var errors=new ArrayList<String>();
        for(String name:processor.classesForEvents(events))
            if(AbstractClassExistsInProject.resolveProjectClass(project,name)==null)
                errors.add("Event class absent from project: "+name+" for "+events);
        return new TraceCheckResult(List.of(),List.of(),errors);
    }
}
