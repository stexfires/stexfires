package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.OneValueRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToOneValueRecordMapper<T extends TextRecord> implements RecordMapper<T, OneValueRecord> {

    private final int valueIndex;

    public ToOneValueRecordMapper() {
        this(Fields.FIRST_FIELD_INDEX);
    }

    public ToOneValueRecordMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public final @NotNull OneValueRecord map(@NotNull T record) {
        return new OneValueRecord(record.category(), record.recordId(),
                record.valueAt(valueIndex));
    }

}
