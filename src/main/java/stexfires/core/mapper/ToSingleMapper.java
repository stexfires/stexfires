package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.record.SingleRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToSingleMapper<T extends TextRecord> implements RecordMapper<T, SingleRecord> {

    private final int valueIndex;

    public ToSingleMapper() {
        this(Fields.FIRST_FIELD_INDEX);
    }

    public ToSingleMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public final @NotNull SingleRecord map(@NotNull T record) {
        return new SingleRecord(record.getCategory(), record.getRecordId(),
                record.getValueAt(valueIndex));
    }

}
