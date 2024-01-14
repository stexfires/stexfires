package stexfires.record;

import org.jspecify.annotations.Nullable;

/**
 * @since 0.1
 */
public interface KeyValueCommentRecord extends KeyValueRecord, CommentRecord {

    KeyValueCommentRecord withValueAndComment(@Nullable String value, @Nullable String comment);

}
