package gradingTools.comp690f26.assignment2._tests;

import gradingTools.comp690f26.assignment1.projectClassesSuite.*;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.A1TracedClassesSuite;
import gradingTools.comp690f26.assignment2.projectClassesSuite.*;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.A2TraceIntegritySuite;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.A2TracedClassesSuite;
import gradingTools.comp690f26.common.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.runners.Suite;

/** Checks role correspondence and gate wiring independently of the reference implementation. */
public final class AssignmentSuiteParityTest {
    public static void main(String[] args) throws Exception {
        compareRoles(A1TracedClassesSuite.class, A1ProjectClassesSuite.class);
        var a1Separation = gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite.class;
        require(tags(A1ProjectClassesSuite.class, AbstractClassExistsInProject.class)
                .equals(tags(a1Separation, AbstractClassIsSeparated.class)),
                "Every A1 project role must have a separation check");
        for (var check : a1Separation.getAnnotation(Suite.SuiteClasses.class).value()) {
            var instance = check.getDeclaredConstructor().newInstance();
            var prerequisites = (Class<?>[]) invoke(instance, "precedingTests");
            require(prerequisites.length == 2
                    && prerequisites[0] == gradingTools.comp690f26.files.AdaptiveTraceFilesProvided.class
                    && AbstractClassExistsInProject.class.isAssignableFrom(prerequisites[1]),
                    "Separation needs only traces and its own project role: " + check);
            require(invoke(instance, "getTag").equals(invoke(
                    prerequisites[1].getDeclaredConstructor().newInstance(), "getTag")),
                    "Separation prerequisite resolves the wrong role: " + check);
        }
        compareRoles(A2TracedClassesSuite.class, A2ProjectClassesSuite.class);
        var roles = Set.of(A2ProjectClassesSuite.roleChecks());
        require(!roles.contains(A2TracedClassesExistInProject.class), "aggregate must not depend on itself");
        var gate = new A2TracedClassesExistInProject();
        require(Set.of((Class<?>[]) invoke(gate, "projectClassTests")).equals(roles), "gate must cover every project role");
        for (var event : A2TraceIntegritySuite.checks())
            require(List.of((Class<?>[]) invoke(event.getDeclaredConstructor().newInstance(), "additionalPrecedingTests"))
                    .stream().noneMatch(type->type==A2TracedClassesExistInProject.class), "Unrelated global project gate: " + event);
        for (var type : gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite.class
                .getAnnotation(Suite.SuiteClasses.class).value()) {
            if (AbstractClassIsSeparated.class.isAssignableFrom(type)) {
                var prerequisites = (Class<?>[]) invoke(type.getDeclaredConstructor().newInstance(), "precedingTests");
                require(Arrays.stream(prerequisites).anyMatch(AbstractClassExistsInProject.class::isAssignableFrom),
                        "Class separation must follow project resolution: " + type);
            }
        }
        System.out.println("AssignmentSuiteParityTest passed: role mappings and independent event gates (relevant-class mutations tested separately)");
    }
    private static void compareRoles(Class<?> traced, Class<?> project) throws Exception {
        require(tags(traced, AbstractClassFoundInTrace.class).equals(tags(project, AbstractClassExistsInProject.class)),
                "Trace/project role mismatch: " + project);
    }
    private static Set<String> tags(Class<?> suite, Class<?> base) throws Exception {
        Set<String> tags = new HashSet<>();
        for (var type : suite.getAnnotation(Suite.SuiteClasses.class).value())
            if (base.isAssignableFrom(type))
                require(tags.add((String) invoke(type.getDeclaredConstructor().newInstance(), "getTag")), "Duplicate role: " + type);
        return tags;
    }
    private static Object invoke(Object value, String method) throws Exception {
        for (Class<?> type = value.getClass(); type != null; type = type.getSuperclass()) {
            for (var candidate : type.getDeclaredMethods())
                if (candidate.getName().equals(method) && candidate.getParameterCount() == 0) {
                    candidate.setAccessible(true);
                    return candidate.invoke(value);
                }
        }
        throw new NoSuchMethodException(method);
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
