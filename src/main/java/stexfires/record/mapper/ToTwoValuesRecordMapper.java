package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.TwoValuesRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToTwoValuesRecordMapper<T extends TextRecord> implements RecordMapper<T, TwoValuesRecord> {

    private final int firstIndex;
    private final int secondIndex;

    public ToTwoValuesRecordMapper() {
        this(Fields.FIRST_FIELD_INDEX, Fields.FIRST_FIELD_INDEX + 1);
    }

    public ToTwoValuesRecordMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public final @NotNull TwoValuesRecord map(@NotNull T record) {
        return new TwoValuesRecord(record.category(), record.recordId(),
                record.valueAt(firstIndex), record.valueAt(secondIndex));
    }

}
