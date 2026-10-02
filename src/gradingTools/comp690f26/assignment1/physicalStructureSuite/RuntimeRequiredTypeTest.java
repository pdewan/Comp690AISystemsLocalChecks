package gradingTools.comp690f26.assignment1.physicalStructureSuite;

import eduAI.trace.TraceObjectPrinter;
import java.net.URI;

public final class RuntimeRequiredTypeTest {
	interface Executor { }
	static final class ActualExecutor implements Executor { URI client = URI.create("https://example.invalid"); }
	static final class EmptyExecutor implements Executor { String unrelated = "java.net.URI"; }
	static final class Adapter { Executor executor; Adapter(Executor value) { executor = value; } }
	public static void main(String[] args) {
		String good = TraceObjectPrinter.format("serverHandle", new Adapter(new ActualExecutor()));
		String bad = TraceObjectPrinter.format("serverHandle", new Adapter(new EmptyExecutor()));
		if (AbstractClassHasRequiredTypes.runtimeRequiredTypePath(good, Adapter.class, URI.class) == null)
			throw new AssertionError("Actual implementation behind interface not followed");
		if (AbstractClassHasRequiredTypes.runtimeRequiredTypePath(bad, Adapter.class, URI.class) != null)
			throw new AssertionError("Unrelated implementing class or string accepted");
		String sibling = TraceObjectPrinter.format("holders", java.util.List.of(new Adapter(new EmptyExecutor()), new ActualExecutor()));
		if (AbstractClassHasRequiredTypes.runtimeRequiredTypePath(sibling, Adapter.class, URI.class) != null)
			throw new AssertionError("Required type in unrelated sibling accepted");
		System.out.println("RuntimeRequiredTypeTest passed");
	}
}
