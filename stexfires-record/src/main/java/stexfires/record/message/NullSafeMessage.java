package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecord;

import java.util.Objects;

/**
 * @since 0.1
 */
public class NullSafeMessage<T extends TextRecord> implements RecordMessage<T> {

    public static final String DEFAULT_RECORD_IS_NULL_MESSAGE = "ERROR: Record is null!";

    private final RecordMessage<? super T> recordMessage;
    private final String recordIsNullMessage;

    public NullSafeMessage(RecordMessage<? super T> recordMessage) {
        this(recordMessage, DEFAULT_RECORD_IS_NULL_MESSAGE);
    }

    public NullSafeMessage(RecordMessage<? super T> recordMessage, @Nullable String recordIsNullMessage) {
        Objects.requireNonNull(recordMessage);
        this.recordMessage = recordMessage;
        this.recordIsNullMessage = recordIsNullMessage;
    }

    @Override
    public final @Nullable String createMessage(T record) {
        return record == null ? recordIsNullMessage : recordMessage.createMessage(record);
    }

}
