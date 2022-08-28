package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface CommentRecord extends TextRecord {

    CommentRecord withComment(@Nullable String comment);

    @NotNull Field commentField();

    default @Nullable String valueOfCommentField() {
        return commentField().value();
    }

    default Optional<String> valueOfCommentFieldAsOptional() {
        return Optional.ofNullable(commentField().value());
    }

}
