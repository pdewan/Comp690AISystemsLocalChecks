package gradingTools.comp690f26.assignment1;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.runners.Suite;

import util.annotations.Explanation;

public final class ExplanationLengthTest {
	public static final int MAX_EXPLANATION_LENGTH = 100;

	private ExplanationLengthTest() {
	}

	public static void assertExplanationsFitTooltips() {
		Set<String> problems = new LinkedHashSet<>();
		check(F26Assignment1Suite.class, new LinkedHashSet<>(), problems);
		if (!problems.isEmpty()) {
			throw new AssertionError(
					"Assignment 1 explanations exceed "
							+ MAX_EXPLANATION_LENGTH
							+ " characters: "
							+ problems);
		}
	}

	private static void check(
			Class<?> aClass,
			Set<Class<?>> aVisited,
			Set<String> aProblems) {
		if (!aVisited.add(aClass)) {
			return;
		}
		Explanation explanation = aClass.getAnnotation(Explanation.class);
		if (explanation != null
				&& explanation.value().length() > MAX_EXPLANATION_LENGTH) {
			aProblems.add(
					aClass.getSimpleName()
							+ "="
							+ explanation.value().length());
		}
		Suite.SuiteClasses suite =
				aClass.getAnnotation(Suite.SuiteClasses.class);
		if (suite != null) {
			for (Class<?> child : suite.value()) {
				check(child, aVisited, aProblems);
			}
		}
	}

	public static void main(String[] args) {
		assertExplanationsFitTooltips();
		System.out.println("All Assignment 1 explanations fit tooltips");
	}
}
