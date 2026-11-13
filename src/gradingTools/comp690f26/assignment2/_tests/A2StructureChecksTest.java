package gradingTools.comp690f26.assignment2._tests;

import gradingTools.comp690f26.assignment2.interfacesSuite.A2TaggedFieldsUseInterfaces;
import gradingTools.comp690f26.common.*;
import java.util.*;


/** Structural mutations: deliberately invalid Java class relationships, not changed model output. */
public final class A2StructureChecksTest {
    public static class Result { }
    public static final class ChildResult extends Result { }
    public static final class WrongResult { }
    public interface Handler { Result process(); }
    public interface Description { String label(); }
    public static class First implements Handler, Description {
        public Result process() { return new Result(); }
        public String label() { return "first"; }
    }
    public static class Second extends First { @Override public ChildResult process() { return new ChildResult(); } }
    public static class NoInterface { public Result process() { return new Result(); } }
    public interface OtherHandler { WrongResult other(); }
    public abstract static class Unrelated implements OtherHandler { }
    public abstract static class Ambiguous implements Handler, OtherHandler { }
    public interface ErasedHandler { Object process(); }
    public abstract static class Erased implements ErasedHandler { }
    public interface NoArgumentNotification { void received(); }
    public abstract static class NoArgumentDisplay implements NoArgumentNotification { }
    public interface ReturningNotification { Result received(Result data); }
    public abstract static class ReturningDisplay implements ReturningNotification { }
    public interface Marker { }
    public abstract static class MarkerOnly implements Marker { }
    public interface Observer { void received(Result value); }
    public interface ChildObserver extends Observer { }
    public abstract static class Display implements ChildObserver { }
    public interface StaticNotification { static void received(Result value) { } }
    public abstract static class StaticDisplay implements StaticNotification { }
    public static class Registry { Map<String, List<? extends Handler[]>> entries; }
    public static class RegistryParent { List<Handler> entries; }
    public static class InheritedRegistry extends RegistryParent { }
    public static class Empty { }
    public static class RawRegistry { Map entries; }
    public static class WrongRegistry { Map<String, OtherHandler> entries; }
    public static class StaticRegistry { static Map<String, Handler> entries; }
    public static class Observable { List<Observer> listeners; }
    public static class StaticObservable { static List<Observer> listeners; }
    public static class ConcreteHolder { First handler; }
    public static class InterfaceHolder { Handler handler; }
    public static class MethodObservable { public boolean arbitrary(String key, Observer listener) { return true; } }
    public static class InheritedMethodObservable extends MethodObservable { }
    public interface ObservableContract { default void arbitrary(ChildObserver listener) { } }
    public static class DefaultMethodObservable implements ObservableContract { }
    public static class StaticMethodObservable { public static void arbitrary(Observer listener) { } }
    public static class PrivateMethodObservable { private void arbitrary(Observer listener) { } }
    public static class ProtectedMethodObservable { protected void arbitrary(Observer listener) { } }
    public static class ConcreteMethodObservable { public void arbitrary(Display listener) { } }
    public static class ObjectMethodObservable { public void arbitrary(Object listener) { } }
    public static class WrongMethodObservable { public void arbitrary(Handler listener) { } }
    public static class MarkerMethodObservable { public void arbitrary(Marker listener) { } }
    public static class ConstructorObservable { public ConstructorObservable(Observer listener) { } }
    public static class BothRegistrationObservable extends MethodObservable {
        public BothRegistrationObservable(Observer listener) { }
    }
    public static class StoredConstructorObservable {
        private final Observer listener;
        public StoredConstructorObservable(String name, Observer listener) { this.listener = listener; }
    }
    public static class PrivateConstructorObservable { private PrivateConstructorObservable(Observer listener) { } }
    public static class ConcreteConstructorObservable { public ConcreteConstructorObservable(Display listener) { } }
    public static class ObjectConstructorObservable { public ObjectConstructorObservable(Object listener) { } }
    public static class WrongConstructorObservable { public WrongConstructorObservable(Handler listener) { } }
    public static class MarkerConstructorObservable { public MarkerConstructorObservable(Marker listener) { } }
    public static class ChildConstructorObservable extends ConstructorObservable {
        public ChildConstructorObservable() { super(null); }
    }
    private static int assertions;
    private static int negatives;

    public static void main(String[] args) throws Exception {
        expect(RoleStructureChecks.singleInterface(List.of(Display.class)), true, "inherited single interface");
        expect(RoleStructureChecks.singleInterface(List.of(First.class)), false, "two unrelated interfaces");
        expect(RoleStructureChecks.singleInterface(List.of(NoInterface.class)), false, "no interface");
        expect(RoleStructureChecks.singleInterface(List.of()), false, "no class evidence");
        expect(RoleStructureChecks.observerNotificationMethods(List.of(Display.class)), true, "inherited notification method");
        for (Class<?> invalid : List.of(MarkerOnly.class, StaticDisplay.class, NoArgumentDisplay.class, ReturningDisplay.class))
            expect(RoleStructureChecks.observerNotificationMethods(List.of(invalid)), false, "invalid notification " + invalid.getSimpleName());
        expect(RoleStructureChecks.observerNotificationMethods(List.of()), false, "no observer evidence");
        for (Class<?> valid : List.of(MethodObservable.class, InheritedMethodObservable.class, DefaultMethodObservable.class,
                ConstructorObservable.class, StoredConstructorObservable.class))
            expect(RoleStructureChecks.observableObserverMethods(List.of(valid), List.of(Display.class)), true,
                "inherited/parent/default notification parameter " + valid.getSimpleName());
        for (Class<?> invalid : List.of(Empty.class, StaticMethodObservable.class, PrivateMethodObservable.class,
                ProtectedMethodObservable.class, ConcreteMethodObservable.class, ObjectMethodObservable.class,
                WrongMethodObservable.class, PrivateConstructorObservable.class, ConcreteConstructorObservable.class,
                ObjectConstructorObservable.class, WrongConstructorObservable.class, ChildConstructorObservable.class))
            expect(RoleStructureChecks.observableObserverMethods(List.of(invalid), List.of(Display.class)), false,
                "invalid observer method " + invalid.getSimpleName());
        expect(RoleStructureChecks.observableObserverMethods(List.of(MarkerMethodObservable.class), List.of(MarkerOnly.class)), false, "marker is not notification contract");
        expect(RoleStructureChecks.observableObserverMethods(List.of(MarkerConstructorObservable.class), List.of(MarkerOnly.class)), false, "constructor marker is not notification contract");
        expect(RoleStructureChecks.observableObserverMethods(List.of(), List.of(Display.class)), false, "missing observable evidence");
        expect(RoleStructureChecks.observableObserverMethods(List.of(MethodObservable.class), List.of()), false, "missing observer evidence");
        expect(RoleStructureChecks.observableObserverMethods(List.of(MethodObservable.class, Empty.class), List.of(Display.class)), false, "every observable must offer method");
        expect(RoleStructureChecks.observableObserverMethods(List.of(MethodObservable.class), List.of(Display.class, MarkerOnly.class)), false, "every observer must be accepted");
        for (Class<?> type : List.of(ConstructorObservable.class, StoredConstructorObservable.class)) {
            var registration = RoleStructureChecks.observableObserverMethods(List.of(type), List.of(Display.class));
            if (!registration.constructorOnly() || !registration.limitations().getFirst().contains("construction time"))
                throw new AssertionError("Constructor-only registration must have a clear partial-credit diagnostic");
        }
        for (Class<?> type : List.of(MethodObservable.class, BothRegistrationObservable.class)) {
            var registration = RoleStructureChecks.observableObserverMethods(List.of(type), List.of(Display.class));
            if (!registration.errors().isEmpty() || registration.constructorOnly())
                throw new AssertionError("Instance registration must receive full credit");
        }
        var incomplete = RoleStructureChecks.observableObserverMethods(
            List.of(ConstructorObservable.class, Empty.class), List.of(Display.class));
        if (incomplete.errors().isEmpty() || incomplete.constructorOnly())
            throw new AssertionError("A missing API must not earn constructor-only credit");
        List<Class<?>> handlers = List.of(First.class, Second.class);
        Set<Class<?>> project = new HashSet<>(Arrays.asList(A2StructureChecksTest.class.getDeclaredClasses()));
        expect(RoleStructureChecks.commonOperationInterface(handlers), true, "common inherited handler interface");
        expect(RoleStructureChecks.commonOperationInterface(List.of(First.class)), true, "one implementation used by multiple properties");
        expect(RoleStructureChecks.commonOperationInterface(List.of()), false, "missing handlers");
        expect(RoleStructureChecks.commonOperationInterface(List.of(NoInterface.class)), false, "missing handler interface");
        expect(RoleStructureChecks.commonOperationInterface(List.of(First.class, Unrelated.class)), false, "unrelated handlers");
        expect(RoleStructureChecks.commonOperationInterface(List.of(Ambiguous.class)), false, "ambiguous operation interfaces");
        expect(RoleStructureChecks.commonOperationInterface(List.of(MarkerOnly.class)), false, "marker is not handler contract");
        expect(RoleStructureChecks.commonResult(handlers, List.of(Result.class, ChildResult.class)), true, "covariant results");
        expect(RoleStructureChecks.commonResult(handlers, List.of(WrongResult.class)), false, "wrong traced result class");
        expect(RoleStructureChecks.commonResult(handlers, List.of()), false, "missing traced result type");
        expect(RoleStructureChecks.commonResult(List.of(Erased.class), List.of(Result.class)), false, "Object does not specify result contract");
        expect(RoleStructureChecks.observerInterface(List.of(Display.class)), true, "inherited observer notification");
        expect(RoleStructureChecks.observerInterface(List.of(NoInterface.class)), false, "missing observer interface");
        expect(RoleStructureChecks.observerInterface(List.of(MarkerOnly.class)), false, "marker observer");
        expect(RoleStructureChecks.observerInterface(List.of(StaticDisplay.class)), false, "static notification is not observer operation");
        expect(RoleStructureChecks.handlerStorage(List.of(Registry.class, InheritedRegistry.class), handlers, project), true, "generic array and inherited storage");
        for (Class<?> invalid : List.of(Empty.class, RawRegistry.class, WrongRegistry.class, StaticRegistry.class))
            expect(RoleStructureChecks.handlerStorage(List.of(invalid), handlers, project), false, "invalid registry " + invalid.getSimpleName());
        expect(RoleStructureChecks.observerStorage(List.of(Observable.class), List.of(Display.class), project), true, "observer parent interface storage");
        // Receiving an argument does not establish storage or actual notification.
        expect(RoleStructureChecks.observerStorage(List.of(ConstructorObservable.class), List.of(Display.class), project), false, "constructor discards observer");
        expect(RoleStructureChecks.observerStorage(List.of(StoredConstructorObservable.class), List.of(Display.class), project), true, "constructor stores observer");
        expect(RoleStructureChecks.observerStorage(List.of(MethodObservable.class), List.of(Display.class), project), false, "registration method discards observer");
        expect(RoleStructureChecks.observableObserverMethods(List.of(Observable.class), List.of(Display.class)), false, "storage alone does not establish registration API");
        expect(RoleStructureChecks.observerStorage(List.of(Empty.class), List.of(Display.class), project), false, "missing observer storage");
        expect(RoleStructureChecks.observerStorage(List.of(StaticObservable.class), List.of(Display.class), project), false, "static-only observer storage");
        var inspect = AbstractTaggedClassPhysicalStructuresUseInterfaces.class.getDeclaredMethod("inspectPhysicalStructure",
                Class.class, String.class, Set.class, Set.class, Set.class, List.class);
        inspect.setAccessible(true);
        for (Class<?> root : List.of(InterfaceHolder.class, ConcreteHolder.class)) {
            List<String> errors = new ArrayList<>();
            inspect.invoke(new A2TaggedFieldsUseInterfaces(), root, root.getName(), Set.of(root, First.class), project, new HashSet<>(), errors);
            expect(errors, root == InterfaceHolder.class, "concrete tagged collaborator " + root.getSimpleName());
        }
        System.out.println("A2StructureChecksTest passed: " + assertions + " cases, " + negatives + " structural mutations rejected");
    }
    private static void expect(RoleStructureChecks.ObserverRegistration result, boolean pass, String label) {
        expect(result.errors(), pass, label);
    }
    private static void expect(List<String> errors, boolean pass, String label) {
        assertions++;
        if (!pass) negatives++;
        if (errors.isEmpty() != pass) throw new AssertionError(label + ": " + errors);
    }
}
