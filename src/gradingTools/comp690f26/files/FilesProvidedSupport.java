package gradingTools.comp690f26.files;

import java.nio.file.Path;
import java.util.Objects;


/** Explicit assignment configuration and the submission directory shared by file tests. */
public final class FilesProvidedSupport {
    private static FilesProvidedConfiguration configuration;
    private static Path directory;
    private static Path resolvedDirectory;

    private FilesProvidedSupport() { }

    public static void configure(FilesProvidedConfiguration selected, Path selectedDirectory) {
        Objects.requireNonNull(selected, "Assignment files configuration is required");
        Objects.requireNonNull(selectedDirectory, "Submission directory is required");
        if (selectedDirectory.toString().isBlank())
            throw new IllegalArgumentException("Submission directory must not be blank");
        configuration = selected;
        directory = selectedDirectory;
        resolvedDirectory = null;
    }

    public static FilesProvidedConfiguration configuration() {
        if (configuration == null)
            throw new IllegalStateException("Assignment files are not configured. Run the A1 or A2 suite configuration before its tests.");
        return configuration;
    }

    public static void setDirectory(String selected) {
        configuration();
        directory = FilesProvidedConfiguration.directory(selected);
        resolvedDirectory = null;
    }

    public static String getDirectory() {
        configuration();
        return directory.toString();
    }

    public static Path resolvedDirectory() {
        FilesProvidedConfiguration selected = configuration();
        if (resolvedDirectory == null)
            resolvedDirectory = Objects.requireNonNull(selected.directoryResolver().apply(directory),
                "Assignment directory resolver returned no directory");
        return resolvedDirectory;
    }
}
