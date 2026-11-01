package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.common.io.*;
import gradingTools.comp690f26.common.expectedRequestContentSuite.*;
import gradingTools.comp690f26.common.commands.*;
import gradingTools.comp690f26.assignment2.*;
import grader.basics.junit.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.files.A2CLIRequestExpectationsProvided;


/** Uses fresh grading executions, not cached successes. Mutates only copied files.
 * Unrelated failed/partial metadata, history, other-view syntax and attachment
 * checks must not veto or scale credit. Relevant bad syntax, missing content and
 * wrong settings must still fail. No production input or trace is modified. */
public final class A2EvidenceIndependenceTest {
    private static final String BROKEN_BODY="invalid message body";
    private static final double PARTIAL_CREDIT=0.5;
    private static final double WRONG_TEMPERATURE=12345.0;
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 directory");
        Path source=Path.of(args[0]);
        Path fixture=Files.createTempDirectory(Path.of("build"),"a2-evidence-independence-");
        for(var name:List.of(CLITraceInformation.TRACE_FILE_NAME,CLITraceInformation.INTERACTIONS_FILE_NAME,
                A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT,A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT))
            Files.copy(source.resolve(name),fixture.resolve(name));
        Path log=fixture.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME);
        String original=Files.readString(log).replace("\r\n","\n");
        A2ChecksConfiguration.setTraceDirectory(fixture.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
        full(A2CLIRequestSettingsMatch.class);
        full(AdaptiveTranscriptCommandResultsMatch.class);
        require(JUnitTestsEnvironment.getPassFailJUnitTest(A2CLIRequestExpectationsProvided.class)==null,
            "Settings/transcript unnecessarily prepared attachment contents");
        full(AdaptiveOrdinaryMessagesCommandResultsMatch.class);
        full(AdaptiveSystemMessagesCommandResultsMatch.class);
        full(AdaptiveGeminiExpectedImageMatchesGenericImage.class);
        full(AdaptiveOllamaExpectedImageMatchesGenericImage.class);
        var io=((CLIOuterOutputSyntax)JUnitTestsEnvironment.getPassFailJUnitTest(CLIOuterOutputSyntax.class)).getInteractions().orElseThrow();
        var unrelated=List.of(
            gradingTools.comp690f26.assignment1.responseTranslationSuite.GeminiMetadataMatchesGenericMetadata.class,
            gradingTools.comp690f26.assignment1.responseTranslationSuite.OllamaMetadataMatchesGenericMetadata.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousSystemPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousSystemPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousUserPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousUserPromptsRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.GeminiPreviousCompletedResponsesRetained.class,
            gradingTools.comp690f26.assignment1.contextHistorySuite.OllamaPreviousCompletedResponsesRetained.class,
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.GeminiMergedStreamingResponseInNextContextWindow.class,
            gradingTools.comp690f26.assignment1.streamingBehaviorSuite.OllamaMergedStreamingResponseInNextContextWindow.class);
        for(var type:unrelated) full(type);
        for(double fraction:List.of(0.0,PARTIAL_CREDIT)) {
            for(var type:unrelated) JUnitTestsEnvironment.getPassFailJUnitTest(type)
                .setLastResult(new TestCaseResult(fraction,"Injected unrelated failure/partial result"));
            full(AdaptiveOrdinaryMessagesCommandResultsMatch.class);
            full(AdaptiveSystemMessagesCommandResultsMatch.class);
            full(AdaptiveGeminiExpectedImageMatchesGenericImage.class);
            full(AdaptiveOllamaExpectedImageMatchesGenericImage.class);
            for(var type:unrelated) require(JUnitTestsEnvironment.getPassFailJUnitTest(type).getLastResult().getPercentage()==fraction,
                "Unrelated result was overwritten: "+type);
        }
        // A required image translation still gates image credit.
        var imageTranslation=JUnitTestsEnvironment.getPassFailJUnitTest(
            gradingTools.comp690f26.assignment1.requestTranslationSuite.GeminiImageMatchesGenericImage.class);
        var saved=imageTranslation.getLastResult();
        imageTranslation.setLastResult(new TestCaseResult(false,"Injected wrong image translation"));
        fails(AdaptiveGeminiExpectedImageMatchesGenericImage.class);
        full(AdaptiveOllamaExpectedImageMatchesGenericImage.class);
        imageTranslation.setLastResult(saved);
        for(var broken:List.of(CLIMessageView.ORDINARY,CLIMessageView.SYSTEM)) {
            String changed=original;
            for(var entry:io.entries()) if(CLICommandNames.MESSAGES.equals(entry.input().commandName()) && broken.includes(entry))
                changed=changed.replace(entry.output().orElseThrow().body(),BROKEN_BODY);
            require(!changed.equals(original),"Output mutation changed nothing");
            Files.writeString(log,changed);
            full(CLIOuterOutputSyntax.class);
            fails(CLIMessagesOutputSyntax.class);
            full(broken==CLIMessageView.ORDINARY?AdaptiveSystemMessagesOutputMatches.class:AdaptiveMessagesOutputMatches.class);
            fails(broken==CLIMessageView.ORDINARY?AdaptiveMessagesOutputMatches.class:AdaptiveSystemMessagesOutputMatches.class);
        }
        Files.writeString(log,original);full(CLIOuterOutputSyntax.class);
        Files.delete(fixture.resolve(A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT));
        Files.delete(fixture.resolve(A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT));
        full(A2CLIRequestExpectationsProvided.class);
        fails(AdaptiveGeminiExpectedImageMatchesGenericImage.class);
        full(A2CLIRequestSettingsMatch.class);
        full(AdaptiveTranscriptCommandResultsMatch.class);
        // Even a partial content-preparation result must not scale these checks.
        JUnitTestsEnvironment.getPassFailJUnitTest(A2CLIRequestExpectationsProvided.class)
            .setLastResult(new TestCaseResult(PARTIAL_CREDIT,"Injected partial attachment preparation"));
        full(A2CLIRequestSettingsMatch.class);full(AdaptiveTranscriptCommandResultsMatch.class);
        var processor=A2ChecksConfiguration.createTraceFilesProcessor(fixture);processor.processTraceFiles();
        var wrong=new CLIMixedIO(io.entries().stream().map(e->{
            var c=e.input();var values=new LinkedHashMap<>(c.properties());
            if(c.kind()==CLIInteraction.Kind.PROMPT) values.put(eduAI.properties.AIPropertyNames.TEMPERATURE,WRONG_TEMPERATURE);
            return new CLIMixedIO.Entry(e.number(),new CLIInteraction.Command(c.inputOrdinal(),c.lineNumber(),c.rawInput(),
                c.kind(),c.commandName(),c.argument(),c.propertyName(),values),e.attachments(),e.output(),e.chunks());
        }).toList());
        require(!CLITraceExpectations.settingsFrom(wrong).check(processor.getTraceRecords().values(),null,
            CLITraceExpectations.Check.SETTINGS).passed(),"Wrong settings passed without attachments");
        try {
            CLITraceExpectations.settingsFrom(io).check(processor.getTraceRecords().values(),null,CLITraceExpectations.Check.IMAGES);
            throw new AssertionError("Settings projection used as image expectations");
        } catch(IllegalStateException expected) { }
        System.out.println("A2EvidenceIndependenceTest passed: fresh fail/partial-credit controls for all five fixes; relevant failures still rejected. Fixture: "+fixture);
        System.exit(0);
    }
    private static TestCaseResult run(Class<?> type) {
        var runner=new org.junit.runner.JUnitCore();var listener=new AJUnitTestResult();runner.addListener(listener);
        runner.run(type);var result=listener.getTestCaseResult();
        JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);
        return result;
    }
    private static void full(Class<?> type) {var r=run(type);require(r.getPercentage()==1,"Expected full independent credit: "+type+": "+r.getNotes());}
    private static void fails(Class<?> type) {var r=run(type);require(r.isFail(),"Expected relevant failure: "+type);}
    private static void require(boolean condition,String message) {if(!condition)throw new AssertionError(message);}
}
