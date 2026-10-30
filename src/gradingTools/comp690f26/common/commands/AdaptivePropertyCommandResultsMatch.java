package gradingTools.comp690f26.common.commands;
/**
 * Uses property-handler names and values traced before each property-command return. Checks all returned properties, including a multi-property getter, without consulting the input file or required script.
 * Success: timeout=120 in the property trace and a command result containing timeout and 120. Failure: the result contains 240, loses the property name, or drops one property from a multi-property return. No property commands or no property-return/value evidence means no credit. The separate input check detects mutually consistent traces that disagree with user input.
 */
@util.annotations.Explanation("Compares property-command results with the property names and values traced within that command. Supports multiple property returns; requires usable evidence and no I/O file.")
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.COMMAND_RESULT_CONTAINMENT)
public class AdaptivePropertyCommandResultsMatch extends AbstractCommandResultCheck {
    @Override protected String commandName() { return eduAI.trace.processors.cli.CLICommandNames.PROPERTY; }
    @Override protected boolean requiresInputs() { return false; }
}
