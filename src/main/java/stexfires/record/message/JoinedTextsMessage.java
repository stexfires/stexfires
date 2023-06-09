package stexfires.record.message;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.util.Objects;

/**
 * @since 0.1
 */
public class JoinedTextsMessage<T extends TextRecord> implements RecordMessage<T> {

    private final CharSequence delimiter;

    public JoinedTextsMessage() {
        this(TextFields.DEFAULT_FIELD_TEXT_DELIMITER);
    }

    public JoinedTextsMessage(CharSequence delimiter) {
        Objects.requireNonNull(delimiter);
        this.delimiter = delimiter;
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return TextFields.joinTexts(record, delimiter);
    }

}
