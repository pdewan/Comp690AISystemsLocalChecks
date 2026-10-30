package gradingTools.comp690f26.common;

import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public final class PrecedingTestUtils {
    public static Class<?>[] forProvidedFiles(Class<?>[] tests) {
        var excluded = FilesProvidedSupport.configuration().checks().excludedPrerequisites();
        return Arrays.stream(tests).filter(test -> !excluded.contains(test)).toArray(Class<?>[]::new);
    }
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
