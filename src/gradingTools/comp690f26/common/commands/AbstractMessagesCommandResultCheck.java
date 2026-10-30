package gradingTools.comp690f26.common.commands;

import eduAI.trace.processors.cli.*;

/** Shares message comparison and explicit attachment-kind policy. Correct model
 * response history survives a bad prompt-handler result; missing model answers
 * still fail. Raw prompt references require no files; expansion reads text only. */
public abstract class AbstractMessagesCommandResultCheck extends AbstractCommandResultCheck {
    protected abstract CLIMessageView view();
    // Only data preceding the selected displays supplies their comparison oracle.
    @Override protected boolean requiresPriorResponses() { return true; }
    @Override protected boolean requiresPriorParameters() { return true; }
    @Override protected String commandName() { return CLICommandNames.MESSAGES; }
    @Override protected boolean requiresInputs() { return true; }
    @Override protected boolean selectedInput(CLIMixedIO.Entry entry) { return view().includes(entry); }
    @Override protected void configureSessionRules(CLICommandResultCheckerRegistry registry,CLIMixedIO io,java.nio.file.Path directory) throws java.io.IOException {
        registry.register(commandName(),new CLIMessagesCommandResultChecker(io,directory,view(),gradingTools.comp690f26.files.FilesProvidedSupport.configuration().checks().attachmentKinds()));
    }
}
