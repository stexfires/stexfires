package stexfires.record.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Fields;
import stexfires.record.TextRecord;
import stexfires.record.impl.StandardRecord;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ToStandardMapper<T extends TextRecord> implements RecordMapper<T, StandardRecord> {

    public ToStandardMapper() {
    }

    @SuppressWarnings("CastToConcreteClass")
    @Override
    public final @NotNull StandardRecord map(@NotNull T record) {
        if (record instanceof StandardRecord) {
            return (StandardRecord) record;
        }
        return new StandardRecord(record.category(), record.recordId(),
                Fields.collectValues(record));
    }

}
