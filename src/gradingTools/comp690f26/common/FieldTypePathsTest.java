package gradingTools.comp690f26.common;

import static org.junit.Assert.*;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/** Structural evidence only: does not instantiate or execute student classes. */
public class FieldTypePathsTest {
    private static final long CYCLE_TEST_TIMEOUT_MILLIS = 1000;
    interface Transport { }
    interface NestedTransport { }
    static class Adapter { Transport transport; }
    static class NativeTransport implements Transport { NestedTransport nested; }
    static class NativeLeaf implements NestedTransport { URI client; }
    static class EmptyTransport implements Transport { String text; }
    static abstract class AbstractTransport implements Transport { }
    static class Unrelated { URI client; }
    static class StaticTransport implements Transport { static URI client; }
    static class CycleTransport implements Transport { Adapter back; }
    static class CycleWithClient implements Transport { Adapter back; URI client; }
    static class Direct { URI client; }
    static class Inherited extends Direct { }

    private String path(Class<?> root, Class<?>... project) {
        return FieldTypePaths.findRequiredTypePath(root, URI.class, Set.of(project),
                new HashSet<>(), root.getName());
    }

    // Pass through two uniquely implemented interfaces, irrespective of names.
    // Abstract implementations cannot be instantiated and do not cause ambiguity.
    @Test public void followsUniqueImplementationsAcrossMultipleLinks() {
        // GraderBasics getAllClasses excludes interfaces; discovery must use the
        // field's declared interface and the supplied concrete project classes.
        String result = path(Adapter.class, Adapter.class,
                AbstractTransport.class, NativeTransport.class, NativeLeaf.class);
        assertNotNull(result);
        assertTrue(result.contains(NativeTransport.class.getName()));
        assertTrue(result.contains(NativeLeaf.class.getName()));
    }

    // A missing implementation or a client in an unrelated class is not evidence.
    @Test public void rejectsMissingAndUnrelatedImplementations() {
        assertNull(path(Adapter.class, Adapter.class, Transport.class, Unrelated.class));
        assertNull(path(Adapter.class, Adapter.class, Transport.class, EmptyTransport.class, Unrelated.class));
        assertNull(path(Adapter.class, Adapter.class, Transport.class, NativeTransport.class,
                NestedTransport.class));
    }

    // Even one good candidate cannot resolve two concrete implementations.
    @Test public void rejectsAmbiguousImplementations() {
        assertNull(path(Adapter.class, Adapter.class, Transport.class, NativeTransport.class,
                EmptyTransport.class, NestedTransport.class, NativeLeaf.class));
    }

    // A field must belong to an instance; a static native client earns no credit.
    @Test public void rejectsStaticClient() {
        assertNull(path(Adapter.class, Adapter.class, Transport.class, StaticTransport.class));
    }

    // A cycle terminates; encountering one does not suppress a different valid branch.
    @Test(timeout = CYCLE_TEST_TIMEOUT_MILLIS) public void terminatesCyclesAndExploresOtherFields() {
        assertNull(path(Adapter.class, Adapter.class, Transport.class, CycleTransport.class));
        assertNotNull(path(Adapter.class, Adapter.class, Transport.class, CycleWithClient.class));
    }

    // Preserve direct and inherited instance-field behavior of the original checks.
    @Test public void preservesDirectAndInheritedFields() {
        assertNotNull(path(Direct.class, Direct.class));
        assertNotNull(path(Inherited.class, Inherited.class, Direct.class));
    }
}
