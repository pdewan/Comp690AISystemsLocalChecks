package gradingTools.comp690f26.assignment2.tracedClassesSuite;

import gradingTools.comp690f26.common.GradingCheckWeights;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;

@Explanation("Identifies the additional CLI roles required by A2 from trace evidence.")
@RunWith(Suite.class)
@Suite.SuiteClasses({
    CLICommandDispatcherFoundInTrace.class,
    CommandHandlerRegistryFoundInTrace.class,
    PromptCommandHandlerFoundInTrace.class,
    PropertyCommandHandlerFoundInTrace.class,
    TranscriptCommandHandlerFoundInTrace.class,
    MessagesCommandHandlerFoundInTrace.class,
    CLIPropertyHandlersFoundInTrace.class,
    ExitCommandHandlerFoundInTrace.class,
    CLICommandOutputObserverFoundInTrace.class,
    CLIStreamOutputObserverFoundInTrace.class,
    CLIStreamObservableFoundInTrace.class,
    CLITextFileExpanderFoundInTrace.class,
    CLIImageFileExpanderFoundInTrace.class,
    CLIPropertyHandlerRegistryFoundInTrace.class
})
@MaxValue(GradingCheckWeights.EVIDENCE_GATE)
public class A2TracedClassesSuite { }
