package gradingTools.comp690f26.assignment2._tests;
import static gradingTools.comp690f26.assignment2._tests.A1A2ScopeRegressionTest.*;
import java.util.*;
import java.nio.file.*;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.*;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.*;
import grader.basics.junit.*;
import eduAI.trace.processors.*;
import gradingTools.comp690f26.assignment2.A2ChecksConfiguration;
import gradingTools.comp690f26.assignment1.A1ChecksConfiguration;

import eduAI.trace.lib.LibTraceEvent;
import gradingTools.comp690f26.assignment1.F26Assignment1Suite;
import gradingTools.comp690f26.assignment2.F26Assignment2Suite;



/** Both assignments/providers accept correct metadata and reject a wrong supplied value. */
public final class MetadataPrerequisitesTest {
    private static final String METADATA_VALUE = "propertyValue";
    private static final String WRONG_VALUE_PREFIX = "invalid_metadata_value_";
    private record Case(String provider, Class<?> check) { }

    public static void main(String[] args) throws Exception {
        if(args.length != 2) throw new IllegalArgumentException("Supply A1 and A2 correct directories");
        grader.basics.config.BasicExecutionSpecificationSelector.getBasicExecutionSpecification().setLogTestData(false);
        for(boolean a2 : List.of(false,true)) {
            var source=Path.of(args[a2?1:0]);
            if(a2) A2ChecksConfiguration.setTraceDirectory(source.toString());
            else A1ChecksConfiguration.setTraceDirectory(source.toString());
            var processor=a2?A2ChecksConfiguration.createTraceFilesProcessor(source):A1ChecksConfiguration.createTraceFilesProcessor(source);
            processor.processTraceFiles();
            var records=processor.getTraceRecords().values();
            var originals=new LinkedHashMap<Path,List<String>>();
            for(var record:records) {
                var file=Path.of(record.getTraceFileName());
                if(!originals.containsKey(file)) originals.put(file,Files.readAllLines(file));
            }
            var fixture=Files.createTempDirectory(Path.of("build"),"metadata-prerequisites-");
            if(a2) {
                for(var name:List.of(CLITraceInformation.INTERACTIONS_FILE_NAME,A2ChecksConfiguration.REQUIRED_IMAGE_ATTACHMENT,A2ChecksConfiguration.REQUIRED_TEXT_ATTACHMENT))
                    Files.copy(source.resolve(name),fixture.resolve(name));
                A2ChecksConfiguration.setTraceDirectory(fixture.toString());
                BasicJUnitUtils.toGradableTree(F26Assignment2Suite.class);
            } else {
                A1ChecksConfiguration.setTraceDirectory(fixture.toString());
                BasicJUnitUtils.toGradableTree(F26Assignment1Suite.class);
            }
            for(var test:List.of(new Case(TraceProviderNames.GEMINI,GeminiMetadataMatchesGenericMetadata.class),
                    new Case(TraceProviderNames.OLLAMA,OllamaMetadataMatchesGenericMetadata.class))) {
                restore(originals,fixture);
                full(test.check());
                var metadata=first(records,test.provider(),LibTraceEvent.METADATA_TRANSLATED);
                restore(originals,fixture);
                var file=Path.of(metadata.getTraceFileName());
                mutate(fixture.resolve(file.getFileName()),originals.get(file),metadata,
                    line->line.replace(METADATA_VALUE+"=\"",METADATA_VALUE+"=\""+WRONG_VALUE_PREFIX));
                full(MetadataTranslatedTraced.class);reject(test.check());
                System.out.println("Metadata controls passed: "+(a2?"A2":"A1")+" "+test.provider());
            }
        }
        System.out.println("MetadataPrerequisitesTest passed: both assignments/providers, baseline and incorrect-value rejection");
        System.exit(0);
    }
    private static TraceRecord first(Collection<TraceRecord> records,String provider,LibTraceEvent event) {
        return records.stream().filter(r->provider.equals(r.getId().provider())
            && event.name().toLowerCase(Locale.ROOT).equals(r.getTraceLine().getEventName()))
            .min(Comparator.comparingInt(TraceRecord::getTraceFileLineNumber)).orElseThrow();
    }
    private static void restore(Map<Path,List<String>> originals,Path fixture) throws Exception {
        for(var entry:originals.entrySet()) Files.write(fixture.resolve(entry.getKey().getFileName()),entry.getValue());
        JUnitTestsEnvironment.clearCachedJUnitTestCases();
    }
}
