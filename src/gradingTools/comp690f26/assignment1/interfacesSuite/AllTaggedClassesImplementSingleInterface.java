package gradingTools.comp690f26.assignment1.interfacesSuite;

import grader.basics.junit.NotAutomatableException;
import grader.basics.junit.TestCaseResult;
import grader.basics.project.NotGradableException;
import grader.basics.project.Project;
import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.projectClassesSuite.Assignment1ProjectClassTests;
import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.annotations.Explanation;
import util.annotations.MaxValue;
import util.trace.Tracer;

@MaxValue(5)
@Explanation("Checks one most-specific interface per required class and per optional class present in traces.")
public class AllTaggedClassesImplementSingleInterface
		extends PassFailJUnitTestCase {
    /** Missing optional merger roles do not veto or scale regular points. Required
     * roles still veto. The aggregate gate rejects any traced class absent from
     * the project; present optional classes undergo the same interface checks.
     * Thus no merger can pass, but a traced nonexistent merger cannot pass. */
    @Override protected boolean failedTestVetoes(Class type) {
        return !Assignment1ProjectClassTests.isOptional(type);
    }
    @Override protected boolean shouldScaleResult() { return false; }

	@Override
	protected Class[] precedingTests() {
		return Assignment1ProjectClassTests.interfacePrerequisites();
	}

	@Override
	public TestCaseResult test(Project aProject, boolean anInteractive)
			throws NotAutomatableException, NotGradableException {
		Set<Class<?>> taggedClasses = new LinkedHashSet<>();
		for (PassFailJUnitTestCase precedingTest :
				getPrecedingTestInstances()) {
			if (precedingTest instanceof AbstractClassExistsInProject classTest) {
				taggedClasses.addAll(classTest.getTracedClasses());
			}
		}

		Map<String, List<String>> invalidClasses = new LinkedHashMap<>();
		for (Class<?> taggedClass : taggedClasses) {
			Set<Class<?>> interfaces = mostSpecificInterfaces(
					allInterfaces(taggedClass));
			List<String> interfaceNames = classNames(interfaces);
			if (interfaces.size() != 1) {
				invalidClasses.put(taggedClass.getName(), interfaceNames);
			} else {
				Tracer.info(
						"Tagged class implements one interface: class="
								+ taggedClass.getName()
								+ " interface="
								+ interfaceNames.get(0));
			}
		}

		if (!invalidClasses.isEmpty()) {
			String message =
					"Tagged classes must each implement exactly one interface: "
							+ invalidClasses;
			Tracer.error(message);
			return fail(message);
		}
		return pass();
	}

	private Set<Class<?>> allInterfaces(Class<?> aClass) {
		Set<Class<?>> result = new LinkedHashSet<>();
		for (Class<?> current = aClass;
				current != null && current != Object.class;
				current = current.getSuperclass()) {
			for (Class<?> anInterface : current.getInterfaces()) {
				addInterfaceHierarchy(anInterface, result);
			}
		}
		return result;
	}

	private void addInterfaceHierarchy(
			Class<?> anInterface,
			Set<Class<?>> aResult) {
		if (!aResult.add(anInterface)) {
			return;
		}
		for (Class<?> parent : anInterface.getInterfaces()) {
			addInterfaceHierarchy(parent, aResult);
		}
	}

	private Set<Class<?>> mostSpecificInterfaces(Set<Class<?>> someInterfaces) {
		Set<Class<?>> result = new LinkedHashSet<>(someInterfaces);
		for (Class<?> possibleParent : someInterfaces) {
			for (Class<?> possibleChild : someInterfaces) {
				if (!possibleParent.equals(possibleChild)
						&& possibleParent.isAssignableFrom(possibleChild)) {
					result.remove(possibleParent);
					break;
				}
			}
		}
		return result;
	}

	private List<String> classNames(Set<Class<?>> someClasses) {
		List<String> result = new ArrayList<>();
		for (Class<?> type : someClasses) {
			result.add(type.getName());
		}
		return result;
	}
}
