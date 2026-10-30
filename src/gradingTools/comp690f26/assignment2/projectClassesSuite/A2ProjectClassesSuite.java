package gradingTools.comp690f26.assignment2.projectClassesSuite;

import gradingTools.comp690f26.common.AbstractClassExistsInProject;
import gradingTools.comp690f26.common.PrecedingTestUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.util.Arrays;
import util.annotations.Explanation;

@Explanation("Checks that the additional CLI classes required by A2 exist in the project.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2TracedClassesExistInProject.class,
    CLICommandDispatcherExistsInProject.class,
    CommandHandlerRegistryExistsInProject.class,
    PromptCommandHandlerExistsInProject.class,
    PropertyCommandHandlerExistsInProject.class,
    TranscriptCommandHandlerExistsInProject.class,
    MessagesCommandHandlerExistsInProject.class,
    CLIPropertyHandlersExistInProject.class,
    ExitCommandHandlerExistsInProject.class,
    CLICommandOutputObserverExistsInProject.class,
    CLIStreamOutputObserverExistsInProject.class,
    CLIStreamObservableExistsInProject.class,
    CLITextFileExpanderExistsInProject.class,
    CLIImageFileExpanderExistsInProject.class,
    CLIPropertyHandlerRegistryExistsInProject.class
})
public class A2ProjectClassesSuite {
    /** General interface rules examine all A2 roles, so require the project
     * aggregate as well as the individual checks that supply resolved classes. */
    public static Class<?>[] interfacePrerequisites() {
        return PrecedingTestUtils.add(roleChecks(), A2TracedClassesExistInProject.class);
    }
    /** Individual role checks only; excludes the aggregate to avoid a prerequisite cycle. */
    public static Class<?>[] roleChecks() {
        return java.util.Arrays.stream(A2ProjectClassesSuite.class.getAnnotation(Suite.SuiteClasses.class).value())
            .filter(AbstractClassExistsInProject.class::isAssignableFrom)
            .toArray(Class<?>[]::new);
    }
}
