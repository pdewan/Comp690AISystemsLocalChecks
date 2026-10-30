package gradingTools.comp690f26.assignment2.physicalStructureSuite;

import gradingTools.comp690f26.assignment2.support.AbstractA2StructureCheck;

@util.annotations.Explanation("Command registries retain handlers through their shared interface.")
/**
 * Uses resolved role classes and follows their declared instance-field types.
 * A path to the required interface passes; absent paths fail. Missing evidence earns no credit.
 */
@util.annotations.MaxValue(gradingTools.comp690f26.common.GradingCheckWeights.PHYSICAL_RELATIONSHIP)
public class A2CommandRegistryHandlerTypes extends AbstractA2StructureCheck {
    @Override protected Rule rule() { return Rule.COMMAND_REGISTRY; }
}
