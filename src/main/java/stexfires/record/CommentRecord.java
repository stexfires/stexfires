package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface CommentRecord extends TextRecord {

    @NotNull CommentRecord withComment(@Nullable String comment);

    @NotNull Field commentField();

    default @Nullable String comment() {
        return commentField().text();
    }

    default @NotNull Optional<String> commentAsOptional() {
        return commentField().asOptional();
    }

}
