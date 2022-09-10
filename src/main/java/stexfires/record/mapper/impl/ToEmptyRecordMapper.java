package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.mapper.RecordMapper;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToEmptyRecordMapper<T extends TextRecord> implements RecordMapper<T, EmptyRecord> {

    public ToEmptyRecordMapper() {
    }

    @Override
    public @NotNull EmptyRecord map(@NotNull T record) {
        return TextRecords.empty();
    }

}
