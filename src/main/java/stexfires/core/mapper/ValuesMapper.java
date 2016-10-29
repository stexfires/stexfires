package stexfires.core.mapper;

import stexfires.core.Record;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.StandardRecord;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mathias Kalb
 * @see stexfires.core.mapper.fieldvalue.IndexedFieldValueMapper
 * @since 0.1
 */
public class ValuesMapper<T extends Record> implements RecordMapper<T, Record> {

    protected final FieldValueMapper fieldValueMapper;

    public ValuesMapper(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        this.fieldValueMapper = fieldValueMapper;
    }

    @Override
    public Record map(T record) {
        return new StandardRecord(record.getCategory(), record.getRecordId(),
                record.streamOfFields()
                      .map(fieldValueMapper::mapToValue)
                      .collect(Collectors.toList()));
    }

}
