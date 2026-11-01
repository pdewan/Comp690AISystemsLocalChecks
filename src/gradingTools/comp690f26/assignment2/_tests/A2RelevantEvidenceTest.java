package gradingTools.comp690f26.assignment2._tests;
import static eduAI.trace.cli.MinimalCLITrace.*;
import java.util.function.*;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.common.commands.*;
import gradingTools.comp690f26.assignment2.*;
import grader.basics.junit.*;
import eduAI.trace.processors.cli.*;
import eduAI.trace.processors.*;
import eduAI.trace.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;

import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch;



/** Missing model evidence fails choices. Missing submitted expander classes
 * block only their own file kind and preserve the independent aggregate grade. */
public final class A2RelevantEvidenceTest {
    private static final String MISSING_CLASS="missing.assignment.ClassThatDoesNotExist";
    private static final double PARTIAL_CREDIT=0.5;
    private static Path fixture;
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply correct A2 directory");
        Path source=Path.of(args[0]); A2ChecksConfiguration.setTraceDirectory(args[0]);
        var processor=A2ChecksConfiguration.createTraceFilesProcessor(source);processor.processTraceFiles();
        var records=new ArrayList<>(processor.getTraceRecords().values());
        records.sort(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber));
        var io=A2ChecksConfiguration.createInteractionsProcessor().process(source.resolve(CLITraceInformation.INTERACTIONS_FILE_NAME)).interaction().orElseThrow();
        var contracts=A2ChecksConfiguration.createPropertyCheckers();
        var noModels=records.stream().filter(r->!eduAI.properties.AIPropertyNames.CURRENT_MODELS.equals(r.getTraceLine().getAuxiliaryData().get(NAME))).toList();
        fail(CLIPropertyChoicesChecks.check(CLIPropertyChoicesChecks.Check.MODEL_CHOICES,noModels,A2ChecksConfiguration.propertyExpectations()),"model evidence still required");
        fixture=Files.createTempDirectory(Path.of("build"),"a2-relevant-evidence-");
        for(var name:List.of(CLITraceInformation.TRACE_FILE_NAME,CLITraceInformation.INTERACTIONS_FILE_NAME,
                A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT,A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT)) Files.copy(source.resolve(name),fixture.resolve(name));
        var raw=Files.readAllLines(source.resolve(CLITraceInformation.TRACE_FILE_NAME));
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        A2ChecksConfiguration.setTraceDirectory(fixture.toString());BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
        var text=records.stream().filter(r->CLITraceEvidence.is(r,Event.FILE_EXPANDED)
            && A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT.equals(r.getTraceLine().getAuxiliaryData().get(INPUT_FILE_NAME))).findFirst().orElseThrow();
        var image=records.stream().filter(r->CLITraceEvidence.is(r,Event.FILE_EXPANDED)
            && A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT.equals(r.getTraceLine().getAuxiliaryData().get(INPUT_FILE_NAME))).findFirst().orElseThrow();
        mutate(raw,text,line->line.replace(className(text,EXPANDER),MISSING_CLASS));
        var aggregate=gradingTools.comp690f26.assignment2.projectClassesSuite.A2TracedClassesExistInProject.class;
        reject(aggregate);
        full(A2CLIImageFileExpansionContentsMatch.class);reject(A2CLITextFileExpansionContentsMatch.class);
        JUnitTestsEnvironment.getPassFailJUnitTest(aggregate).setLastResult(new TestCaseResult(PARTIAL_CREDIT,"Unrelated aggregate partial result"));
        full(A2CLIImageFileExpansionContentsMatch.class);
        require(JUnitTestsEnvironment.getPassFailJUnitTest(aggregate).getLastResult().getPercentage()==PARTIAL_CREDIT,"Scoped check overwrote whole-project score");
        mutate(raw,image,line->line.replace(className(image,EXPANDER),MISSING_CLASS));
        full(A2CLITextFileExpansionContentsMatch.class);reject(A2CLIImageFileExpansionContentsMatch.class);
        System.out.println("A2RelevantEvidenceTest passed: missing model evidence and per-kind project-class controls");System.exit(0);
    }
    private static String className(TraceRecord r,String field) {return r.getTraceLine().getAuxiliaryData().get(field).split("@")[0];}
    private static void mutate(List<String> original,TraceRecord r,UnaryOperator<String> mutation)throws Exception {
        var copy=new ArrayList<>(original);int line=r.getTraceFileLineNumber()-1;copy.set(line,mutation.apply(copy.get(line)));
        require(!copy.equals(original),"Mutation changed nothing");Files.write(fixture.resolve(CLITraceInformation.TRACE_FILE_NAME),copy);
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
    }
    private static TestCaseResult run(Class<?> type) {var core=new org.junit.runner.JUnitCore();var listener=new AJUnitTestResult();core.addListener(listener);core.run(type);var result=listener.getTestCaseResult();JUnitTestsEnvironment.getPassFailJUnitTest(type).setLastResult(result);return result;}
    private static void full(Class<?> type){var r=run(type);require(r.getPercentage()==1,type+": "+r.getNotes());}
    private static void reject(Class<?> type){require(run(type).isFail(),"Relevant failure passed "+type);}
    private static void pass(TraceCheckResult r,String label){require(r.passed(),label+": "+r.level3ErrorMessages());}
    private static void fail(TraceCheckResult r,String label){require(!r.passed(),label);}
    private static void require(boolean ok,String message){if(!ok)throw new AssertionError(message);}
}
