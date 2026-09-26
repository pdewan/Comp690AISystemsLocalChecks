package gradingTools.comp690f26.assignment1._tests;

import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runners.Suite;

import grader.basics.testcase.PassFailJUnitTestCase;
import static gradingTools.comp690f26.assignment1.ExplanationLengthTest.MAX_EXPLANATION_LENGTH;
import util.annotations.Explanation;

/** Maintenance test only: do not add to the student grading suites. */
public final class ExplanationAnnotationsTest {
	private static final Pattern PACKAGE = Pattern.compile("(?m)^package\\s+([^;]+);");

	public static void main(String[] args) throws Exception {
		Path sourceRoot = Path.of(args.length == 0
				? "src/gradingTools/comp690f26/assignment1" : args[0]);
		List<String> failures = new ArrayList<>();
		int checked = 0;
		int longest = 0;
		try (var files = Files.walk(sourceRoot)) {
			for (Path file : files.filter(p -> p.toString().endsWith(".java")).toList()) {
				Matcher declaration = PACKAGE.matcher(Files.readString(file));
				if (!declaration.find()) {
					throw new AssertionError("Missing package in " + file);
				}
				String name = file.getFileName().toString().replaceFirst("\\.java$", "");
				Class<?> type = Class.forName(declaration.group(1).trim() + "." + name,
						false, ExplanationAnnotationsTest.class.getClassLoader());
				boolean suite = type.isAnnotationPresent(Suite.SuiteClasses.class);
				boolean test = PassFailJUnitTestCase.class.isAssignableFrom(type)
						&& !Modifier.isAbstract(type.getModifiers());
				if (!suite && !test) {
					continue;
				}
				checked++;
				Explanation annotation = type.getDeclaredAnnotation(Explanation.class);
				if (annotation == null || annotation.value().isBlank()) {
					failures.add(type.getName() + ": missing explicit explanation");
					continue;
				}
				String text = annotation.value();
				longest = Math.max(longest, text.length());
				if (text.length() > MAX_EXPLANATION_LENGTH || text.contains("\n") || text.contains("\r")) {
					failures.add(type.getName() + ": tooltip must be one line, at most " + MAX_EXPLANATION_LENGTH);
				}
				if (text.startsWith("Groups ")) {
					failures.add(type.getName() + ": explain purpose, not that this is a group");
				}
			}
		}
		if (checked == 0 || !failures.isEmpty()) {
			throw new AssertionError("Checked " + checked + " classes: " + failures);
		}
		System.out.println("Explanation coverage passed: " + checked
				+ " tests/suites; longest tooltip=" + longest + " characters.");
	}
}
