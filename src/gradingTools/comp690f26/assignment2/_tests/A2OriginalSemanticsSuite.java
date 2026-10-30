package gradingTools.comp690f26.assignment2._tests;

import gradingTools.comp690f26.assignment2.parameterHistorySuite.A2CLIParameterHistorySnapshotsPreserved;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIImageFileExpansionContentsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLIRequestSettingsMatch;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLISemanticsContract;
import gradingTools.comp690f26.assignment2.semanticsSuite.A2CLITextFileExpansionContentsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveOrdinaryMessagesCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptivePromptCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptivePropertyCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveStreamingMatchesSetting;
import gradingTools.comp690f26.common.commands.AdaptiveSystemMessagesCommandResultsMatch;
import gradingTools.comp690f26.common.commands.AdaptiveTranscriptCommandResultsMatch;
import gradingTools.comp690f26.common.properties.BooleanPropertyAlternatives;
import gradingTools.comp690f26.common.properties.PropertyAlternativesConsistent;
import gradingTools.comp690f26.common.properties.PropertyGettersMatchSetters;
import gradingTools.comp690f26.common.properties.PropertyResultTypesConsistent;
import gradingTools.comp690f26.common.properties.PropertyStoreConsistent;
import gradingTools.comp690f26.common.properties.RequiredModelAlternatives;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/** Fixed pre-move semantic membership for score and duplication regressions. */
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
    A2CLISemanticsContract.class
})
public class A2OriginalSemanticsSuite { }
