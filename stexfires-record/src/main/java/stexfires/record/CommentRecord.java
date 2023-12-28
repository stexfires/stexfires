package stexfires.record;

import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public interface CommentRecord extends TextRecord {

    CommentRecord withComment(@Nullable String comment);

    TextField commentField();

    @SuppressWarnings("SameReturnValue")
    int commentIndex();

    default @Nullable String comment() {
        return commentField().text();
    }

    default Optional<String> commentAsOptional() {
        return commentField().asOptional();
    }

    default Stream<String> commentAsStream() {
        return commentField().stream();
    }

}
