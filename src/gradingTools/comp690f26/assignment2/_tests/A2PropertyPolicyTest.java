package gradingTools.comp690f26.assignment2._tests;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.common.properties.*;
import gradingTools.comp690f26.assignment2.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.common.AbstractTraceProcessorCheck;

/** Verifies dependency direction and independently configured property input policies. */
public final class A2PropertyPolicyTest {
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply the correct fixture directory");
        Path directory=Path.of(args[0]); A2ChecksConfiguration.configureProperties();
        var processor=A2ChecksConfiguration.createTraceFilesProcessor(directory); processor.processTraceFiles();
        var checks = new Class<?>[]{PropertyHandlersRegistered.class, PropertyHandlerInstancesReused.class,
            PropertyHandlerInstancesSeparated.class, PropertyStoreIdentityConsistent.class,
            PropertyResultTypesConsistent.class, PropertyAlternativesConsistent.class,
            BooleanPropertyAlternatives.class, RequiredModelAlternatives.class,
            PropertyGettersMatchSetters.class, PropertyStoreConsistent.class};
        var method=AbstractTraceProcessorCheck.class.getDeclaredMethod("check",TraceFilesProcessor.class); method.setAccessible(true);
        var prerequisites=AbstractTraceProcessorCheck.class.getDeclaredMethod("additionalPrecedingTests"); prerequisites.setAccessible(true);
        for(var type:checks) {
            var test=type.getConstructor().newInstance();
            // No input prerequisites or injected I/O objects are available here.
            var expected = type == PropertyStoreConsistent.class ? List.of(PropertyStoreIdentityConsistent.class)
                : type == PropertyGettersMatchSetters.class ? List.of(PropertyStoreConsistent.class) : List.of();
            if(!List.of((Class<?>[])prerequisites.invoke(test)).equals(expected))
                throw new AssertionError("Unexpected consistency prerequisites: "+type);
            var result=(TraceCheckResult)method.invoke(test,processor);
            if(!result.passed()) throw new AssertionError(type+": "+result);
        }
        var expectedPrerequisites=AdaptiveExpectedPropertyInputs.class.getDeclaredMethod("additionalPrecedingTests"); expectedPrerequisites.setAccessible(true);
        var dependencies=List.of((Class<?>[])expectedPrerequisites.invoke(new AdaptiveExpectedPropertyInputs()));
        if(!dependencies.containsAll(List.of(PropertyResultTypesConsistent.class,PropertyAlternativesConsistent.class,
                BooleanPropertyAlternatives.class,RequiredModelAlternatives.class,PropertyGettersMatchSetters.class,
                PropertyStoreConsistent.class))) throw new AssertionError("Expected inputs missing semantic prerequisites");
        for(var independent:List.of(PropertyHandlersRegistered.class,PropertyHandlerInstancesReused.class,PropertyHandlerInstancesSeparated.class))
            if(dependencies.contains(independent)) throw new AssertionError("Unrelated structural prerequisite: "+independent);
        var io=A2ChecksConfiguration.createInteractionsProcessor().process(directory.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        var policy=A2ChecksConfiguration.propertyExpectations();
        if(!policy.check(processor.getTraceRecords().values(),io).passed()) throw new AssertionError("Correct session rejected: "+policy.check(processor.getTraceRecords().values(),io));
        var changed=new ArrayList<>(policy.steps()); Collections.reverse(changed);
        if(new CLIPropertyExpectations(policy.descriptors(),changed,policy.requiredModels()).check(processor.getTraceRecords().values(),io).passed()) throw new AssertionError("Configured input order ignored");
        changed=new ArrayList<>(policy.steps()); changed.remove(changed.size()-1);
        if(new CLIPropertyExpectations(policy.descriptors(),changed,policy.requiredModels()).check(processor.getTraceRecords().values(),io).passed()) throw new AssertionError("Configured count ignored");
        var entries=new ArrayList<>(io.entries());
        var entry=entries.get(0); var input=entry.input();
        var values=new LinkedHashMap<>(input.properties()); values.put(input.propertyName(),List.of("different-user-selection"));
        var wrongInput=new CLIInteraction.Command(input.inputOrdinal(),input.lineNumber(),input.rawInput(),input.kind(),input.commandName(),input.argument(),input.propertyName(),values);
        entries.set(0,new CLIMixedIO.Entry(entry.number(),wrongInput,entry.attachments(),entry.output(),entry.chunks()));
        if(policy.check(processor.getTraceRecords().values(),new CLIMixedIO(entries)).passed()) throw new AssertionError("Consistent traces disagreeing with user input were accepted");
        A2ChecksConfiguration.setTraceDirectory(directory.toString());
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        var root=grader.basics.junit.BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class).rootNode;
        var getters=grader.basics.junit.JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(PropertyGettersMatchSetters.class);
        if(!getters.getLastResult().isPass()) throw new AssertionError("Selecting getters first failed");
        for(var prerequisite:List.of(PropertyStoreIdentityConsistent.class,PropertyStoreConsistent.class))
            if(root.findTest(prerequisite).getFractionComplete()!=1)
                throw new AssertionError("Getters did not run store prerequisite: "+prerequisite);
        var selected=grader.basics.junit.JUnitTestsEnvironment.getAndPossiblyRunGradableJUnitTest(AdaptiveExpectedPropertyInputs.class);
        if(selected==null || !selected.getLastResult().isPass()) throw new AssertionError("Selecting expected inputs first did not run its prerequisites successfully");
        System.out.println("A2PropertyPolicyTest passed: independent checks, prerequisite direction, expected session, changed policies, selecting expected inputs first");
        System.exit(0);
    }
}
