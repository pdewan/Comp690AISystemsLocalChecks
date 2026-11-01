package gradingTools.comp690f26.assignment2._tests;
import static gradingTools.comp690f26.assignment2._tests.A1A2ScopeRegressionTest.*;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.common.expectedRequestContentSuite.*;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.*;
import grader.basics.junit.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;

import grader.basics.testcase.PassFailJUnitTestCase;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment1.crossProviderSuite.A1GenericRequestStructuresMatchAcrossProviders;



/** Failed cross-provider grades veto the A1 finale; partial grades retain the
 * framework's partial-credit policy and are not overwritten. Failed translation
 * also blocks prompt credit.
 * These controls test grading dependencies, not trace-emission defects. */
public final class ExpectedPromptPrerequisitesTest {
    private static final double PARTIAL = 0.5;
    private record Case(String provider, String other, Class<?> prompt, Class<?> text) { }
    public static void main(String[] args) throws Exception {
        if(args.length!=1) throw new IllegalArgumentException("Supply A1 correct directory");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        Path source=Path.of(args[0]);
        A1ChecksConfiguration.setTraceDirectory(source.toString());
        var processor=A1ChecksConfiguration.createTraceFilesProcessor(source);processor.processTraceFiles();
        var records=processor.getTraceRecords().values();
        var originals=new LinkedHashMap<Path,List<String>>();
        for(var record:records) {
            var file=Path.of(record.getTraceFileName());
            if(!originals.containsKey(file)) originals.put(file,Files.readAllLines(file));
        }
        Path fixture=Files.createTempDirectory(Path.of("build"),"expected-prompt-prereqs-");
        for(var file:originals.keySet()) Files.write(fixture.resolve(file.getFileName()),originals.get(file));
        A1ChecksConfiguration.setTraceDirectory(fixture.toString());
        BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
        var cross=A1GenericRequestStructuresMatchAcrossProviders.class;
        for(var test:List.of(
            new Case(TraceProviderNames.GEMINI,TraceProviderNames.OLLAMA,AdaptiveGeminiExpectedPromptsInProviderRequests.class,GeminiTextualContextWindowMatchesGenericContextWindow.class),
            new Case(TraceProviderNames.OLLAMA,TraceProviderNames.GEMINI,AdaptiveOllamaExpectedPromptsInProviderRequests.class,OllamaTextualContextWindowMatchesGenericContextWindow.class))) {
            for(var file:originals.keySet()) Files.write(fixture.resolve(file.getFileName()),originals.get(file));
            for(double fraction:List.of(0.0,PARTIAL)) {
                JUnitTestsEnvironment.clearCachedJUnitTestCases();
                ((PassFailJUnitTestCase)cross.getDeclaredConstructor().newInstance()).setLastResult(new TestCaseResult(fraction,"Independent comparison"));
                if(fraction==0) reject(test.prompt()); else full(test.prompt());
                require(JUnitTestsEnvironment.getPassFailJUnitTest(cross).getLastResult().getPercentage()==fraction,"Prompt check changed cross-provider credit");
            }
            JUnitTestsEnvironment.getPassFailJUnitTest(test.text()).setLastResult(new TestCaseResult(false,"Required text translation"));
            reject(test.prompt());
        }
        System.out.println("ExpectedPromptPrerequisitesTest passed: both providers, cross-provider failure gates, retained partial grades and translation vetoes");
        System.exit(0);
    }
}
