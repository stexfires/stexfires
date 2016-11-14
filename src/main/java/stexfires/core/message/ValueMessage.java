package stexfires.core.message;

import stexfires.core.Field;
import stexfires.core.Record;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.ValueRecord;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMessage<T extends Record> implements RecordMessage<T> {

    public static final String DEFAULT_NULL_FIELD_MESSAGE = null;

    protected final Function<? super T, Field> fieldFunction;
    protected final String nullFieldMessage;
    protected final FieldValueMapper fieldValueMapper;

    public ValueMessage(int index) {
        this(record -> record.getFieldAt(index), DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(int index,
                        String nullFieldMessage,
                        FieldValueMapper fieldValueMapper) {
        this(record -> record.getFieldAt(index), nullFieldMessage, fieldValueMapper);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction) {
        this(fieldFunction, DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        String nullFieldMessage,
                        FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldValueMapper);
        this.fieldFunction = fieldFunction;
        this.nullFieldMessage = nullFieldMessage;
        this.fieldValueMapper = fieldValueMapper;
    }

    public static <T extends KeyRecord> ValueMessage<T> keyField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(KeyRecord::getKeyField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
    }

    public static <T extends ValueRecord> ValueMessage<T> valueField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(ValueRecord::getValueField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
    }

    @Override
    public String createMessage(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldMessage;
        }
        return fieldValueMapper.mapToValue(field);
    }

}
