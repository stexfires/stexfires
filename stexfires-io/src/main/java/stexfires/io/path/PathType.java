package stexfires.io.path;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

/**
 * @see java.nio.file.Path
 * @see java.nio.file.attribute.BasicFileAttributes
 * @since 0.1
 */
public enum PathType {

    DIRECTORY,
    REGULAR_FILE,
    SYMBOLIC_LINK,
    OTHER;

    public static PathType ofAttributes(@NotNull BasicFileAttributes fileAttributes) {
        Objects.requireNonNull(fileAttributes);
        if (fileAttributes.isDirectory()) {
            return DIRECTORY;
        } else if (fileAttributes.isRegularFile()) {
            return REGULAR_FILE;
        } else if (fileAttributes.isSymbolicLink()) {
            return SYMBOLIC_LINK;
        }
        return OTHER;
    }

    public static PathType ofPath(@NotNull Path path) throws IOException {
        Objects.requireNonNull(path);
        return ofAttributes(Files.readAttributes(path, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS));
    }

}
