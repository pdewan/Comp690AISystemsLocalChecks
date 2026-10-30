package gradingTools.comp690f26.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/** Reflection checks over role classes supplied by trace/project prerequisites. */
public final class RoleStructureChecks {
    private RoleStructureChecks() { }

    public static Set<Class<?>> operationInterfaces(List<Class<?>> classes) {
        Set<Class<?>> common = new LinkedHashSet<>();
        boolean first = true;
        for (Class<?> type : classes) {
            Set<Class<?>> interfaces = InterfaceStructure.allInterfaces(type);
            interfaces.removeIf(candidate -> Arrays.stream(candidate.getMethods())
                    .noneMatch(method -> dataResult(method, classes)));
            if (first) { common.addAll(interfaces); first = false; }
            else common.retainAll(interfaces);
        }
        return InterfaceStructure.mostSpecificInterfaces(common);
    }

    private static boolean dataResult(Method method, List<Class<?>> handlers) {
        Class<?> result = method.getReturnType();
        return !Modifier.isStatic(method.getModifiers()) && !result.isPrimitive() && !result.isEnum()
                && result != Object.class && !CharSequence.class.isAssignableFrom(result)
                && handlers.stream().noneMatch(result::isAssignableFrom);
    }

    public static List<String> commonOperationInterface(List<Class<?>> classes) {
        if (classes.isEmpty()) return List.of("No handler classes supplied by project prerequisites");
        var interfaces = operationInterfaces(classes);
        return interfaces.size() == 1 ? List.of() : List.of(
                "Expected one common interface defining a result-returning operation for " + classes + "; found " + interfaces);
    }

    public static List<String> commonResult(List<Class<?>> handlers, List<Class<?>> returnedTypes) {
        if (returnedTypes.isEmpty()) return List.of("No readable property-result classes in the traces");
        var failures = commonOperationInterface(handlers);
        if (!failures.isEmpty()) return failures;
        Class<?> contract = operationInterfaces(handlers).iterator().next();
        for (Method operation : contract.getMethods()) {
            if (!dataResult(operation, handlers)) continue;
            boolean compatible = true;
            for (Class<?> handler : handlers) {
                try {
                    Method actual = handler.getMethod(operation.getName(), operation.getParameterTypes());
                    compatible &= operation.getReturnType().isAssignableFrom(actual.getReturnType());
                } catch (NoSuchMethodException e) { compatible = false; }
            }
            compatible &= returnedTypes.stream().allMatch(operation.getReturnType()::isAssignableFrom);
            if (compatible) return List.of();
        }
        return List.of("Property handlers have no shared operation compatible with traced result types " + returnedTypes + ": " + handlers);
    }

    /** Counts inherited interfaces after eliminating their less-specific parents. */
    public static List<String> singleInterface(List<Class<?>> classes) {
        if (classes.isEmpty()) return List.of("No tagged classes supplied by project prerequisites");
        List<String> failures = new ArrayList<>();
        for (Class<?> type : classes) {
            var interfaces = InterfaceStructure.mostSpecificInterfaces(InterfaceStructure.allInterfaces(type));
            if (interfaces.size() != 1)
                failures.add("Expected one most-specific interface: " + type.getName() + "; found " + interfaces);
        }
        return failures;
    }

    /** Checks notification shape only; interface count is a separate general rule. */
    public static List<String> observerNotificationMethods(List<Class<?>> observers) {
        if (observers.isEmpty()) return List.of("No observer classes supplied by project prerequisites");
        List<String> failures = new ArrayList<>();
        for (Class<?> observer : observers)
            if (InterfaceStructure.allInterfaces(observer).stream().noneMatch(RoleStructureChecks::hasNotificationMethod))
                failures.add("No interface instance void method accepting notification data: " + observer.getName());
        return failures;
    }

    public static List<String> observerInterface(List<Class<?>> observers) {
        List<String> failures = new ArrayList<>(singleInterface(observers));
        failures.addAll(observerNotificationMethods(observers));
        return failures;
    }

    private static boolean hasNotificationMethod(Class<?> type) {
        return Arrays.stream(type.getMethods()).anyMatch(method -> !Modifier.isStatic(method.getModifiers())
                && method.getReturnType() == void.class && method.getParameterCount() > 0);
    }

    /**
     * Looks for a public instance method accepting a notification interface of each
     * observed class. Names, return types and additional parameters are unrestricted.
     * This proves API shape, not that the method actually registers its argument.
     */
    public static List<String> observableObserverMethods(List<Class<?>> observables, List<Class<?>> observers) {
        if (observables.isEmpty()) return List.of("No observable classes supplied by project prerequisites");
        if (observers.isEmpty()) return List.of("No observer classes supplied by project prerequisites");
        List<String> failures = new ArrayList<>();
        for (Class<?> observer : observers) {
            var interfaces = InterfaceStructure.allInterfaces(observer);
            interfaces.removeIf(type -> !hasNotificationMethod(type));
            for (Class<?> observable : observables) {
                boolean found = Arrays.stream(observable.getMethods())
                    .filter(method -> !Modifier.isStatic(method.getModifiers()))
                    .flatMap(method -> Arrays.stream(method.getParameterTypes()))
                    .anyMatch(interfaces::contains);
                if (!found) failures.add("No public instance method on " + observable.getName()
                    + " accepts a notification interface of " + observer.getName() + ": " + interfaces);
            }
        }
        return failures;
    }

    public static List<String> handlerStorage(List<Class<?>> registries, List<Class<?>> handlers, Set<Class<?>> project) {
        var failures = commonOperationInterface(handlers);
        if (!failures.isEmpty()) return failures;
        return storedTypes(registries, operationInterfaces(handlers), project);
    }

    public static List<String> observerStorage(List<Class<?>> observables, List<Class<?>> observers, Set<Class<?>> project) {
        var failures = observerInterface(observers);
        if (!failures.isEmpty()) return failures;
        List<String> missing = new ArrayList<>();
        for (Class<?> observer : observers) {
            var interfaces = InterfaceStructure.allInterfaces(observer);
            interfaces.removeIf(type -> !hasNotificationMethod(type));
            missing.addAll(storedTypes(observables, interfaces, project));
        }
        return missing;
    }

    private static List<String> storedTypes(List<Class<?>> owners, Set<Class<?>> required, Set<Class<?>> project) {
        if (owners.isEmpty()) return List.of("No owning classes supplied by project prerequisites");
        List<String> failures = new ArrayList<>();
        for (Class<?> owner : owners) {
            boolean found = required.stream().anyMatch(type -> FieldTypePaths.findRequiredTypePath(
                    owner, type, project, new HashSet<>(), owner.getName()) != null);
            if (!found) failures.add("No instance-field path from " + owner.getName() + " to required interface " + required);
        }
        return failures;
    }
}
