package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

/**
 * @since 0.1
 */
public class RecordIdMessage<T extends TextRecord> implements RecordMessage<T> {

    public static final String DEFAULT_PREFIX = "#";
    public static final String DEFAULT_MISSING_RECORD_ID_MESSAGE = "";

    private final @Nullable String prefix;
    private final @Nullable String missingRecordIdMessage;

    public RecordIdMessage() {
        this(DEFAULT_PREFIX, DEFAULT_MISSING_RECORD_ID_MESSAGE);
    }

    public RecordIdMessage(@Nullable String prefix, @Nullable String missingRecordIdMessage) {
        this.prefix = prefix;
        this.missingRecordIdMessage = missingRecordIdMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        if (record.hasRecordId()) {
            if (prefix != null) {
                return prefix + record.recordIdAsString();
            } else {
                return record.recordIdAsString();
            }
        } else {
            return missingRecordIdMessage;
        }
    }

}
