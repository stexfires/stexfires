package stexfires.core.mapper;

import stexfires.core.mapper.fieldvalue.ConstantFieldValueMapper;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.ValueRecord;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMapper implements UnaryRecordMapper<ValueRecord> {

    protected final FieldValueMapper fieldValueMapper;

    public ValueMapper(String constantValue) {
        this(new ConstantFieldValueMapper(constantValue));
    }

    public ValueMapper(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        this.fieldValueMapper = fieldValueMapper;
    }

    @Override
    public ValueRecord map(ValueRecord record) {
        return record.newValueRecord(fieldValueMapper.mapToValue(record.getValueField()));
    }

}
