package stexfires.record.message;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class RecordIdMessage<T extends TextRecord> implements RecordMessage<T> {

    public static final String DEFAULT_PREFIX = "#";
    public static final String DEFAULT_MISSING_RECORD_ID_MESSAGE = "";

    private final String prefix;
    private final String missingRecordIdMessage;

    public RecordIdMessage() {
        this(DEFAULT_PREFIX, DEFAULT_MISSING_RECORD_ID_MESSAGE);
    }

    public RecordIdMessage(String prefix, String missingRecordIdMessage) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(missingRecordIdMessage);
        this.prefix = prefix;
        this.missingRecordIdMessage = missingRecordIdMessage;
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return record.hasRecordId() ? prefix + record.recordId() : missingRecordIdMessage;
    }

}
