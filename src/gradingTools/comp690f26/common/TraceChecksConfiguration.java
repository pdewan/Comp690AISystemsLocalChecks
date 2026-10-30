package gradingTools.comp690f26.common;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/** Prerequisite choices supplied explicitly by each assignment suite. */
public record TraceChecksConfiguration(Set<Class<?>> excludedPrerequisites,
        Function<String, Class<?>[]> expectedContentPrerequisites,
        Function<String, Class<?>[]> expectedImagePrerequisites,
        java.util.Optional<eduAI.trace.processors.cli.CLIPropertyExpectations> propertyExpectations,
        java.util.Optional<CLICommandChecksConfiguration> commandChecks,
        eduAI.trace.processors.cli.CLIAttachmentKinds attachmentKinds,
        TraceEventEvidencePolicy traceEventEvidence,
        java.util.List<Class<?>> propertyInputPrerequisites, java.util.List<Class<?>> commandInputPrerequisites) {
    public TraceChecksConfiguration {
        excludedPrerequisites = Set.copyOf(excludedPrerequisites);
        Objects.requireNonNull(expectedContentPrerequisites);
        Objects.requireNonNull(expectedImagePrerequisites);
        Objects.requireNonNull(propertyExpectations);
        Objects.requireNonNull(commandChecks);
        Objects.requireNonNull(attachmentKinds);
        Objects.requireNonNull(traceEventEvidence);
        propertyInputPrerequisites = java.util.List.copyOf(propertyInputPrerequisites);
        commandInputPrerequisites = java.util.List.copyOf(commandInputPrerequisites);
    }
}
