package stexfires.record.mapper.impl;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.record.mapper.RecordMapper;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ToValueFieldRecordMapper<T extends TextRecord> implements RecordMapper<T, ValueFieldRecord> {

    private final int valueIndex;

    public ToValueFieldRecordMapper(int valueIndex) {
        this.valueIndex = valueIndex;
    }

    @Override
    public @NotNull ValueFieldRecord map(@NotNull T record) {
        return new ValueFieldRecord(record.category(), record.recordId(),
                record.textAt(valueIndex));
    }

}
