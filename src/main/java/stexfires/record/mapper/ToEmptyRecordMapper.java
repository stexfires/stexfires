package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToEmptyRecordMapper<T extends TextRecord> implements RecordMapper<T, EmptyRecord> {

    public ToEmptyRecordMapper() {
    }

    @Override
    public final @NotNull EmptyRecord map(@NotNull T record) {
        return TextRecords.empty();
    }

}
