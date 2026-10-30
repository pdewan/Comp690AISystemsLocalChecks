package gradingTools.comp690f26.assignment2.support;

import grader.basics.junit.*;
import grader.basics.project.*;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandDispatcherExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLICommandOutputObserverExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIPropertyHandlerRegistryExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIPropertyHandlersExistInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamObservableExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CLIStreamOutputObserverExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.CommandHandlerRegistryExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.ExitCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.MessagesCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.PromptCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.PropertyCommandHandlerExistsInProject;
import gradingTools.comp690f26.assignment2.projectClassesSuite.TranscriptCommandHandlerExistsInProject;
import gradingTools.comp690f26.common.*;
import java.util.*;


public abstract class AbstractA2StructureCheck extends PassFailJUnitTestCase {
    protected enum Rule { COMMAND_INTERFACE, PROPERTY_INTERFACE, PROPERTY_RESULT, SINGLE_INTERFACE, OBSERVER_NOTIFICATION_METHODS, COMMAND_REGISTRY, PROPERTY_REGISTRY, DISPATCHER_OBSERVERS, STREAM_OBSERVERS, DISPATCHER_OBSERVER_METHODS, STREAM_OBSERVER_METHODS }
    protected abstract Rule rule();
    private static final Class<?>[] COMMANDS = {PromptCommandHandlerExistsInProject.class,
        PropertyCommandHandlerExistsInProject.class, TranscriptCommandHandlerExistsInProject.class,
        MessagesCommandHandlerExistsInProject.class, ExitCommandHandlerExistsInProject.class};

    @Override protected Class[] precedingTests() {
        return switch (rule()) {
            case COMMAND_INTERFACE -> COMMANDS.clone();
            case PROPERTY_INTERFACE -> new Class<?>[]{CLIPropertyHandlersExistInProject.class};
            case PROPERTY_RESULT -> new Class<?>[]{gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class, CLIPropertyHandlersExistInProject.class};
            case SINGLE_INTERFACE -> gradingTools.comp690f26.assignment2.projectClassesSuite.A2ProjectClassesSuite.interfacePrerequisites();
            case OBSERVER_NOTIFICATION_METHODS -> new Class<?>[]{CLICommandOutputObserverExistsInProject.class, CLIStreamOutputObserverExistsInProject.class};
            case COMMAND_REGISTRY -> PrecedingTestUtils.add(COMMANDS, CommandHandlerRegistryExistsInProject.class);
            case PROPERTY_REGISTRY -> new Class<?>[]{CLIPropertyHandlersExistInProject.class, CLIPropertyHandlerRegistryExistsInProject.class};
            case STREAM_OBSERVER_METHODS -> new Class<?>[]{CLIStreamObservableExistsInProject.class, CLIStreamOutputObserverExistsInProject.class};
            case STREAM_OBSERVERS -> new Class<?>[]{CLIStreamObservableExistsInProject.class, CLIStreamOutputObserverExistsInProject.class};
            case DISPATCHER_OBSERVER_METHODS -> new Class<?>[]{CLICommandDispatcherExistsInProject.class, CLICommandOutputObserverExistsInProject.class};
            case DISPATCHER_OBSERVERS -> new Class<?>[]{CLICommandDispatcherExistsInProject.class, CLICommandOutputObserverExistsInProject.class};
        };
    }
    private List<Class<?>> classes(Class<?>... tests) {
        Set<Class<?>> result = new LinkedHashSet<>();
        for (var prerequisite : getPrecedingTestInstances())
            if (Arrays.asList(tests).contains(prerequisite.getClass()))
                result.addAll(((AbstractClassExistsInProject) prerequisite).getTracedClasses());
        return List.copyOf(result);
    }
    private List<Class<?>> returnedTypes() {
        final char identitySeparator = '@';
        Set<Class<?>> results = new LinkedHashSet<>();
        var files = (gradingTools.comp690f26.files.AdaptiveTraceFilesProvided) getFirstPrecedingTestInstance();
        for (var record : files.getTraceFilesProcessor().getTraceRecords().values()) {
            if (!eduAI.trace.cli.MinimalCLITrace.Event.PROPERTY_HANDLED.eventName().equals(record.getTraceLine().getEventName())) continue;
            String identity = record.getTraceLine().getAuxiliaryData().get(
                    eduAI.trace.cli.MinimalCLITrace.RESULT + eduAI.trace.cli.TraceDataPaths.ID);
            if (identity == null || identity.lastIndexOf(identitySeparator) <= 0) return List.of();
            String name = identity.substring(0, identity.lastIndexOf(identitySeparator));
            Class<?> type;
            try { type = Class.forName(name, false, classes(CLIPropertyHandlersExistInProject.class).get(0).getClassLoader()); }
            catch (ClassNotFoundException e) { return List.of(); }
            results.add(type);
        }
        return List.copyOf(results);
    }
    @Override public TestCaseResult test(Project project, boolean interactive) throws NotAutomatableException, NotGradableException {
        Set<Class<?>> projectClasses = new HashSet<>();
        for (Object value : BasicProjectIntrospection.getAllClasses(project))
            if (value instanceof Class<?> type) projectClasses.add(type);
        List<String> errors = switch (rule()) {
            case COMMAND_INTERFACE -> RoleStructureChecks.commonOperationInterface(classes(COMMANDS));
            case PROPERTY_INTERFACE -> RoleStructureChecks.commonOperationInterface(classes(CLIPropertyHandlersExistInProject.class));
            case PROPERTY_RESULT -> RoleStructureChecks.commonResult(classes(CLIPropertyHandlersExistInProject.class), returnedTypes());
            // The aggregate supplies a grade, not a class collection.
            case SINGLE_INTERFACE -> RoleStructureChecks.singleInterface(classes(gradingTools.comp690f26.assignment2.projectClassesSuite.A2ProjectClassesSuite.roleChecks()));
            case OBSERVER_NOTIFICATION_METHODS -> RoleStructureChecks.observerNotificationMethods(classes(CLICommandOutputObserverExistsInProject.class, CLIStreamOutputObserverExistsInProject.class));
            case COMMAND_REGISTRY -> RoleStructureChecks.handlerStorage(classes(CommandHandlerRegistryExistsInProject.class), classes(COMMANDS), projectClasses);
            case PROPERTY_REGISTRY -> RoleStructureChecks.handlerStorage(classes(CLIPropertyHandlerRegistryExistsInProject.class), classes(CLIPropertyHandlersExistInProject.class), projectClasses);
            case DISPATCHER_OBSERVER_METHODS -> RoleStructureChecks.observableObserverMethods(classes(CLICommandDispatcherExistsInProject.class), classes(CLICommandOutputObserverExistsInProject.class));
            case STREAM_OBSERVER_METHODS -> RoleStructureChecks.observableObserverMethods(classes(CLIStreamObservableExistsInProject.class), classes(CLIStreamOutputObserverExistsInProject.class));
            case STREAM_OBSERVERS -> RoleStructureChecks.observerStorage(classes(CLIStreamObservableExistsInProject.class), classes(CLIStreamOutputObserverExistsInProject.class), projectClasses);
            case DISPATCHER_OBSERVERS -> RoleStructureChecks.observerStorage(classes(CLICommandDispatcherExistsInProject.class), classes(CLICommandOutputObserverExistsInProject.class), projectClasses);
        };
        return errors.isEmpty() ? pass() : fail(String.join("; ", errors));
    }
}
