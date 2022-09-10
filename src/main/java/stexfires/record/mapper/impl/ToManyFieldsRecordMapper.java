package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyFieldsRecord;
import stexfires.record.mapper.RecordMapper;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToManyFieldsRecordMapper<T extends TextRecord> implements RecordMapper<T, ManyFieldsRecord> {

    public ToManyFieldsRecordMapper() {
    }

    @Override
    public @NotNull ManyFieldsRecord map(@NotNull T record) {
        if (record instanceof ManyFieldsRecord) {
            return (ManyFieldsRecord) record;
        }
        return new ManyFieldsRecord(record.category(), record.recordId(),
                record.arrayOfFields());
    }

}
