package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1
 */
public interface KeyValueCommentRecord extends KeyValueRecord, CommentRecord {

    @NotNull KeyValueCommentRecord withValueAndComment(@Nullable String value, @Nullable String comment);

}
