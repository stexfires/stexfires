package stexfires.record.message;

import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.util.*;

/**
 * @since 0.1
 */
public class JoinedTextsMessage<T extends TextRecord> implements NotNullRecordMessage<T> {

    private final CharSequence delimiter;

    public JoinedTextsMessage() {
        this(TextFields.DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public JoinedTextsMessage(CharSequence delimiter) {
        Objects.requireNonNull(delimiter);
        this.delimiter = delimiter;
    }

    @Override
    public final String createMessage(T record) {
        return TextFields.joinTexts(record, delimiter);
    }

}
