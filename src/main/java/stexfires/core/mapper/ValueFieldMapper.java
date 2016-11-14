package stexfires.core.mapper;

import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.ValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueFieldMapper<T extends ValueRecord> implements RecordMapper<T, ValueRecord> {

    protected final FieldValueMapper fieldValueMapper;

    public ValueFieldMapper(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        this.fieldValueMapper = fieldValueMapper;
    }

    @Override
    public ValueRecord map(T record) {
        return record.newValueRecord(fieldValueMapper.mapToValue(record.getValueField()));
    }

}
