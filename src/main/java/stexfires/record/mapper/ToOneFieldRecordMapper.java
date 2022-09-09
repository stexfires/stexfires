package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.OneFieldRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToOneFieldRecordMapper<T extends TextRecord> implements RecordMapper<T, OneFieldRecord> {

    private final int valueIndex;

    public ToOneFieldRecordMapper() {
        this(Fields.FIRST_FIELD_INDEX);
    }

    public ToOneFieldRecordMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public final @NotNull OneFieldRecord map(@NotNull T record) {
        return new OneFieldRecord(record.category(), record.recordId(),
                record.textAt(valueIndex));
    }

}
