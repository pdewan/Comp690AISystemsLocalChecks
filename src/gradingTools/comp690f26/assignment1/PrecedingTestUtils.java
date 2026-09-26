package gradingTools.comp690f26.assignment1;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class PrecedingTestUtils {
	private PrecedingTestUtils() {
	}

	public static Class<?>[] add(
			Class<?>[] inheritedTests,
			Class<?>... additionalTests) {
		Set<Class<?>> result = new LinkedHashSet<>();
		if (inheritedTests != null) {
			result.addAll(Arrays.asList(inheritedTests));
		}
		if (additionalTests != null) {
			result.addAll(Arrays.asList(additionalTests));
		}
		return result.toArray(Class<?>[]::new);
	}
}
