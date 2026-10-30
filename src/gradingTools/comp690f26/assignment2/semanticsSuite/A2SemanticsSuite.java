package gradingTools.comp690f26.assignment2.semanticsSuite;

import gradingTools.comp690f26.assignment2.observerSuite.A2CLICommandObserverOutput;
import gradingTools.comp690f26.assignment2.observerSuite.A2CLIStreamObserverOutput;
import gradingTools.comp690f26.assignment2.parameterHistorySuite.A2CLIParameterHistorySnapshotsPreserved;
import gradingTools.comp690f26.common.commands.AdaptiveOrdinaryMessagesCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptivePromptCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptivePropertyCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveStreamingMatchesSetting;
import gradingTools.comp690f26.common.commands.AdaptiveSystemMessagesCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveTranscriptCommandResultsMatch;
import gradingTools.comp690f26.common.io.CLIMessagesOutputSyntax;
import gradingTools.comp690f26.common.io.CLIOuterOutputSyntax;
import gradingTools.comp690f26.common.io.CLIPropertyOutputSyntax;
import gradingTools.comp690f26.common.io.CLITranscriptOutputSyntax;
import gradingTools.comp690f26.common.properties.BooleanPropertyAlternatives;
import gradingTools.comp690f26.common.properties.PropertyAlternativesConsistent;
import gradingTools.comp690f26.common.properties.PropertyGettersMatchSetters;
import gradingTools.comp690f26.common.properties.PropertyResultTypesConsistent;
import gradingTools.comp690f26.common.properties.PropertyStoreConsistent;
import gradingTools.comp690f26.common.properties.RequiredModelAlternatives;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.annotations.Explanation;
import util.annotations.MaxValue;


/** Groups atomic checks by responsibility. Original suites remain separately available.
 * This grouping preserves each check's evidence requirements and prerequisites. */
@Explanation("Checks CLI results, output syntax, histories, settings and regular/streaming delivery.")
@MaxValue(185)
@RunWith(Suite.class)
@Suite.SuiteClasses({
    PropertyResultTypesConsistent.class,
    PropertyAlternativesConsistent.class,
    BooleanPropertyAlternatives.class,
    RequiredModelAlternatives.class,
    PropertyGettersMatchSetters.class,
    PropertyStoreConsistent.class,
    AdaptivePropertyCommandResultsMatch.class,
    AdaptivePromptCommandResultsMatch.class,
    AdaptiveTranscriptCommandResultsMatch.class,
    AdaptiveOrdinaryMessagesCommandResultsMatch.class,
    AdaptiveSystemMessagesCommandResultsMatch.class,
    A2CLITextFileExpansionContentsMatch.class,
    A2CLIImageFileExpansionContentsMatch.class,
    A2CLIParameterHistorySnapshotsPreserved.class,
    A2CLIRequestSettingsMatch.class,
    AdaptiveStreamingMatchesSetting.class,
    A2CLISemanticsContract.class,
    CLIOuterOutputSyntax.class,
    CLIPropertyOutputSyntax.class,
    CLITranscriptOutputSyntax.class,
    CLIMessagesOutputSyntax.class,
    A2CLIStreamObserverOutput.class,
    A2CLICommandObserverOutput.class
})
public class A2SemanticsSuite { }
