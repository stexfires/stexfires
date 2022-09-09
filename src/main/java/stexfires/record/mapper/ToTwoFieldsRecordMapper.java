package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.TwoFieldsRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToTwoFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, TwoFieldsRecord> {

    private final int firstIndex;
    private final int secondIndex;

    public ToTwoFieldsRecordMapper() {
        this(Fields.FIRST_FIELD_INDEX, Fields.FIRST_FIELD_INDEX + 1);
    }

    public ToTwoFieldsRecordMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public final @NotNull TwoFieldsRecord map(@NotNull T record) {
        return new TwoFieldsRecord(record.category(), record.recordId(),
                record.textAt(firstIndex), record.textAt(secondIndex));
    }

}
