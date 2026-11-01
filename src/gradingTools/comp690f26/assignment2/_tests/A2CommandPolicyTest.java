package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.common.commands.*;
import gradingTools.comp690f26.common.*;
import gradingTools.comp690f26.assignment2.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;


/**
 * Verifies trace-only command checks run without I/O and semantic command checks are
 * prerequisites of expected inputs, never the reverse. Structural checks stay independent. Correct configured input
 * passes; changed order, count, options, attachments or trace numbering fails.
 * Selecting expected inputs first must run and pass its actual prerequisites.
 */
public final class A2CommandPolicyTest {
    private static int assertions;
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply the correct fixture directory");
        Path directory=Path.of(args[0]);A2ChecksConfiguration.configureProperties();
        var processor=A2ChecksConfiguration.createTraceFilesProcessor(directory);processor.processTraceFiles();
        var records=processor.getTraceRecords().values();
        var invoke=AbstractTraceProcessorCheck.class.getDeclaredMethod("check",TraceFilesProcessor.class);invoke.setAccessible(true);
        var prerequisites=AbstractTraceProcessorCheck.class.getDeclaredMethod("additionalPrecedingTests");prerequisites.setAccessible(true);
        List<Class<?>> traceChecks=List.of(CommandHandlersRegistered.class,CommandHandlerInstancesReused.class,
            AdaptivePropertyCommandResultsMatch.class,AdaptivePromptCommandResultsMatch.class);
        for(var type:traceChecks) {
            var test=type.getConstructor().newInstance();
            require(((Class<?>[])prerequisites.invoke(test)).length==0,"Unexpected I/O prerequisite: "+type);
            require(((TraceCheckResult)invoke.invoke(test,processor)).passed(),"Trace-only check failed: "+type);
        }
        var dependencies=List.of((Class<?>[])prerequisites.invoke(new AdaptiveExpectedCommandInputs()));
        require(dependencies.containsAll(List.of(AdaptivePropertyCommandResultsMatch.class,AdaptivePromptCommandResultsMatch.class)),
            "Expected inputs missing result consistency prerequisites");
        for(var independent:List.of(CommandHandlersRegistered.class,CommandHandlerInstancesReused.class))
            require(!dependencies.contains(independent),"Unrelated structural prerequisite: "+independent);
        for(var type:List.of(AdaptiveTranscriptCommandResultsMatch.class,AdaptiveOrdinaryMessagesCommandResultsMatch.class,AdaptiveSystemMessagesCommandResultsMatch.class)) {
            require(dependencies.contains(type),"Expected inputs must trigger history results");
            var actual=List.of((Class<?>[])prerequisites.invoke(type.getConstructor().newInstance()));
            require(!actual.contains(AdaptiveExpectedCommandInputs.class),"Reversed prerequisite direction");
        }
        var io=A2ChecksConfiguration.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        var policy=A2ChecksConfiguration.commandExpectations();
        require(policy.check(records,io).passed(),"Correct configured session rejected");
        var changed=new ArrayList<>(policy.steps());Collections.reverse(changed);
        require(!new CLICommandExpectations(changed).check(records,io).passed(),"Configured order ignored");
        changed=new ArrayList<>(policy.steps());changed.remove(changed.size()-1);
        require(!new CLICommandExpectations(changed).check(records,io).passed(),"Configured count ignored");
        changed=new ArrayList<>(policy.steps());
        int messages=java.util.stream.IntStream.range(0,changed.size()).filter(i->policy.steps().get(i).command().equals("messages")).findFirst().orElseThrow();
        var step=changed.get(messages);changed.set(messages,new CLICommandExpectations.Step(step.command(),Optional.of("-system"),step.attachments()));
        require(!new CLICommandExpectations(changed).check(records,io).passed(),"Configured options ignored");
        changed=new ArrayList<>(policy.steps());
        int attached=java.util.stream.IntStream.range(0,changed.size()).filter(i->!policy.steps().get(i).attachments().isEmpty()).findFirst().orElseThrow();
        step=changed.get(attached);changed.set(attached,new CLICommandExpectations.Step(step.command(),step.argument(),List.of("wrong.png")));
        require(!new CLICommandExpectations(changed).check(records,io).passed(),"Configured attachment ignored");
        var entries=new ArrayList<>(io.entries());var first=entries.get(0);
        entries.set(0,new CLIMixedIO.Entry(first.number()+1,first.input(),first.attachments(),first.output(),first.chunks()));
        require(!policy.check(records,new CLIMixedIO(entries)).passed(),"Trace/input numbering mismatch ignored");
        // Only prop repeats: unrelated required assignment commands must not be needed for reuse.
        var onlyProps=records.stream().filter(r->CLITraceEvidence.commandResult(r)&&CLICommandNames.PROPERTY.equals(CLITraceEvidence.commandName(r))).toList();
        require(CLICommandHandlerChecks.check(onlyProps,CLICommandHandlerChecks.Check.REUSE).passed(),"Unrelated commands incorrectly required");
        require(!CLICommandHandlerChecks.check(onlyProps.subList(0,1),CLICommandHandlerChecks.Check.REUSE).passed(),"One observation incorrectly proves reuse");
        A2ChecksConfiguration.setTraceDirectory(directory.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        grader.basics.junit.BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
        var selected=grader.basics.junit.JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(AdaptiveExpectedCommandInputs.class);
        require(selected!=null&&selected.getLastResult().isPass(),"Selecting expected inputs first failed");
        System.out.println("A2CommandPolicyTest passed "+assertions+" assertions, including selecting expected inputs first");
        System.exit(0);
    }
    private static void require(boolean condition,String label) { assertions++;if(!condition)throw new AssertionError(label); }
}
