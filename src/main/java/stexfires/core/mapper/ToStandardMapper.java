package stexfires.core.mapper;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Fields;
import stexfires.core.TextRecord;
import stexfires.core.record.StandardRecord;

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
        return new StandardRecord(record.getCategory(), record.getRecordId(),
                Fields.collectValues(record));
    }

}
