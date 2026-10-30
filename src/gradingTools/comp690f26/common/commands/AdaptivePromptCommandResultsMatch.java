package gradingTools.comp690f26.common.commands;
/**
 * Requires only observed providers; accumulation is required only for streaming requests.
 * An Ollama-only nonstreaming session needs no Gemini or streaming evidence.
 * Requires A1 response translation correctness first: a corrupt generic response
 * must not validate a matching but equally corrupt command result. Regular A1
 * chunk-accumulation checks validate completed streaming answers before those
 * answers are used as the comparison oracle. Missing/lost chunks block this
 * check; optional final callbacks do not affect its credit.
 * Uses completed model responses within each prompt command interval, without predicting model wording or requiring an input file. An empty traced command name denotes an ordinary prompt.
 * Success: every recorded model answer occurs in the returned result. Failure: an answer is lost or replaced, or the command has no completed model response evidence. No prompts means insufficient evidence and no credit.
 */
@util.annotations.Explanation("After observed-provider response checks and streaming accumulation when exercised, compares prompt results with completed model responses from the same command. Missing or changed responses fail; no usable prompt evidence earns no credit. No I/O file is required.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMAND_RESULT_CONTAINMENT)
public class AdaptivePromptCommandResultsMatch extends AbstractCommandResultCheck {
    @Override protected Class<?>[] evidencePrecedingTests(eduAI.trace.processors.TraceFilesProcessor processor) {
        return gradingTools.comp690f26.common.CLIProviderPrerequisites.checks(processor,
            gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.RESPONSE,
            gradingTools.comp690f26.common.CLIProviderPrerequisites.Evidence.ACCUMULATION);
    }
    @Override protected String commandName() { return eduAI.trace.processors.cli.CLICommandNames.PROMPT; }
    @Override protected boolean requiresInputs() { return false; }
}
