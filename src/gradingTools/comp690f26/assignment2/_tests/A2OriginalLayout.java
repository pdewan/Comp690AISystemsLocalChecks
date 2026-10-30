package gradingTools.comp690f26.assignment2._tests;

import eduAI.trace.processors.CLITraceInformation;
import eduAI.trace.processors.NativeTraceFilesProcessor;
import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.cli.CLIMixedIOProcessor;
import eduAI.trace.processors.cli.CLIProcessors;
import eduAI.trace.processors.cli.CLIPropertyDescriptor;
import eduAI.trace.processors.cli.CLIPropertyDescriptor.ValueType;
import grader.basics.config.BasicExecutionSpecificationSelector;
import grader.basics.junit.BasicJUnitUtils;
import grader.basics.project.BasicProject;
import gradingTools.comp690f26.assignment1.commonInterfacesSuite.A1CommonInterfacesSuite;
import gradingTools.comp690f26.assignment1.interfacesSuite.A1InterfacesSuite;
import gradingTools.comp690f26.assignment1.physicalStructureSuite.A1PhysicalStructureSuite;
import gradingTools.comp690f26.assignment1.projectClassesSuite.A1ProjectClassesSuite;
import gradingTools.comp690f26.assignment1.providerIndependentTypeIntegritySuite.ProviderIndependentTypeIntegritySuite;
import gradingTools.comp690f26.assignment1.requestTranslationSuite.RequestTranslationSuite;
import gradingTools.comp690f26.assignment1.responseTranslationSuite.ResponseTranslationSuite;
import gradingTools.comp690f26.assignment1.separatedClassesSuite.A1SeparatedClassesSuite;
import gradingTools.comp690f26.assignment1.streamingBehaviorSuite.StreamingBehaviorSuite;
import gradingTools.comp690f26.assignment1.traceIntegritySuite.A1TraceIntegritySuite;
import gradingTools.comp690f26.assignment1.tracedClassesSuite.A1TracedClassesSuite;
import gradingTools.comp690f26.assignment2.*;
import gradingTools.comp690f26.assignment2.commonInterfacesSuite.A2CommonInterfacesSuite;
import gradingTools.comp690f26.assignment2.interfacesSuite.A2InterfacesSuite;
import gradingTools.comp690f26.assignment2.observerSuite.A2ObserverObservableSuite;
import gradingTools.comp690f26.assignment2.physicalStructureSuite.A2PhysicalStructureSuite;
import gradingTools.comp690f26.assignment2.projectClassesSuite.A2ProjectClassesSuite;
import gradingTools.comp690f26.assignment2.separatedClassesSuite.A2SeparationOfConcernsSuite;
import gradingTools.comp690f26.assignment2.suites.A2ExpectedContentSuite;
import gradingTools.comp690f26.assignment2.suites.A2HandlerRegistrationSuite;
import gradingTools.comp690f26.assignment2.traceIntegritySuite.A2TraceIntegritySuite;
import gradingTools.comp690f26.assignment2.tracedClassesSuite.A2TracedClassesSuite;
import gradingTools.comp690f26.common.contextHistorySuite.ContextHistorySuite;
import gradingTools.comp690f26.common.expectedRequestContentSuite.AdaptiveExpectedRequestContentSuite;
import gradingTools.comp690f26.common.io.A2InputOutputConsistencySuite;
import gradingTools.comp690f26.common.io.A2OutputSyntaxSuite;
import gradingTools.comp690f26.files.A2FilesProvidedSuite;
import gradingTools.comp690f26.files.FilesProvidedConfiguration;
import gradingTools.comp690f26.files.FilesProvidedConfiguration.InteractionsSource;
import gradingTools.comp690f26.files.FilesProvidedSupport;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import trace.grader.basics.GraderBasicsTraceUtility;
import util.annotations.Explanation;
import util.trace.Tracer;

/** Frozen pre-grouping membership used only to verify unchanged leaf scores. */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    A2FilesProvidedSuite.class,
    A2OutputSyntaxSuite.class,
    A2InputOutputConsistencySuite.class,
    A1TraceIntegritySuite.class,
    A2TraceIntegritySuite.class,
    // Keep released scored suites at this level: the grader flattens nested suites.
    A1TracedClassesSuite.class,
    A1ProjectClassesSuite.class,
    A1SeparatedClassesSuite.class,
    A1PhysicalStructureSuite.class,
    A1InterfacesSuite.class,
    A1CommonInterfacesSuite.class,
    ProviderIndependentTypeIntegritySuite.class,
    AdaptiveExpectedRequestContentSuite.class,
    ResponseTranslationSuite.class,
    RequestTranslationSuite.class,
    StreamingBehaviorSuite.class,
    // A2-only groups remain separate from the reused A1 suites.
    ContextHistorySuite.class,
    A2TracedClassesSuite.class,
    A2ProjectClassesSuite.class,
    A2SeparationOfConcernsSuite.class,
    A2HandlerRegistrationSuite.class,
    A2PhysicalStructureSuite.class,
    A2InterfacesSuite.class,
    A2CommonInterfacesSuite.class,
    A2ObserverObservableSuite.class,
    A2OriginalSemanticsSuite.class,
    A2ExpectedContentSuite.class
})
public class A2OriginalLayout { }
