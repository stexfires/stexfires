package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.TwoFieldsRecord;
import stexfires.record.mapper.RecordMapper;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToTwoFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, TwoFieldsRecord> {

    private final int firstIndex;
    private final int secondIndex;

    public ToTwoFieldsRecordMapper(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    @Override
    public @NotNull TwoFieldsRecord map(@NotNull T record) {
        return new TwoFieldsRecord(record.category(), record.recordId(),
                record.textAt(firstIndex), record.textAt(secondIndex));
    }

}
