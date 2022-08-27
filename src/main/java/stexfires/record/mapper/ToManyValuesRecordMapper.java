package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.ManyValuesRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToManyValuesRecordMapper<T extends TextRecord> implements RecordMapper<T, ManyValuesRecord> {

    public ToManyValuesRecordMapper() {
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public final @NotNull ManyValuesRecord map(@NotNull T record) {
        if (record instanceof ManyValuesRecord) {
            return (ManyValuesRecord) record;
        }
        return new ManyValuesRecord(record.category(), record.recordId(),
                Fields.collectValues(record));
    }

}
