package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public interface CommentRecord extends TextRecord {

    @NotNull CommentRecord withComment(@Nullable String comment);

    @NotNull TextField commentField();

    @SuppressWarnings("SameReturnValue")
    int commentIndex();

    default @Nullable String comment() {
        return commentField().text();
    }

    default @NotNull Optional<String> commentAsOptional() {
        return commentField().asOptional();
    }

    default @NotNull Stream<String> commentAsStream() {
        return commentField().stream();
    }

}
