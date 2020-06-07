package stexfires.core.message;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Fields;
import stexfires.core.TextRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class JoinedValuesMessage<T extends TextRecord> implements RecordMessage<T> {

    private final CharSequence delimiter;

    public JoinedValuesMessage() {
        this(Fields.DEFAULT_FIELD_VALUE_DELIMITER);
    }

    public JoinedValuesMessage(CharSequence delimiter) {
        Objects.requireNonNull(delimiter);
        this.delimiter = delimiter;
    }

    @Override
    public final @NotNull String createMessage(T record) {
        return Fields.joinValues(record, delimiter);
    }

}
