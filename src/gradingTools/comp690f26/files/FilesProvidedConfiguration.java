package gradingTools.comp690f26.files;

import eduAI.trace.processors.TraceFilesProcessor;
import eduAI.trace.processors.cli.CLIMixedIOProcessor;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;


/** Assignment-specific file selection and processors, shared by the file prerequisites. */
public record FilesProvidedConfiguration(Function<Path, Path> directoryResolver,
		Function<Path, TraceFilesProcessor> traceProcessorFactory,
		Optional<InteractionsSource> interactions,
        gradingTools.comp690f26.common.TraceChecksConfiguration checks) {
	/** Explicit suite launch directory; never selected by the shared file tests. */
	public static final Path WORKING_DIRECTORY = Path.of(".");
	public static Path directory(String selected) {
		if (selected == null || selected.isBlank())
			throw new IllegalArgumentException("Submission directory must not be null or blank");
		return Path.of(selected.trim());
	}
	public FilesProvidedConfiguration {
		Objects.requireNonNull(directoryResolver);
		Objects.requireNonNull(traceProcessorFactory);
		Objects.requireNonNull(interactions);
        Objects.requireNonNull(checks);
	}

	public record InteractionsSource(String fileName, Supplier<CLIMixedIOProcessor> processorFactory) {
		public InteractionsSource {
			Objects.requireNonNull(fileName);
			Objects.requireNonNull(processorFactory);
		}
	}
}
