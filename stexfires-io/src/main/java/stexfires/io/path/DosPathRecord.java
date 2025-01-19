package stexfires.io.path;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.util.*;

/**
 * @since 0.1
 */
public interface DosPathRecord extends PathRecord {

    String EXTENSION_SEPARATOR = ".";

    TextField archiveField();

    TextField readOnlyField();

    TextField hiddenField();

    TextField systemField();

    TextField fileExtensionField();

    default boolean isArchive() {
        return Boolean.parseBoolean(Objects.requireNonNull(archiveField().text()));
    }

    default boolean isReadOnly() {
        return Boolean.parseBoolean(Objects.requireNonNull(readOnlyField().text()));
    }

    default boolean isHidden() {
        return Boolean.parseBoolean(Objects.requireNonNull(hiddenField().text()));
    }

    default boolean isSystem() {
        return Boolean.parseBoolean(Objects.requireNonNull(systemField().text()));
    }

    default @Nullable String fileExtension() {
        return fileExtensionField().text();
    }

    default Optional<String> fileExtensionAsOptional() {
        return fileExtensionField().asOptional();
    }

}
