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
    protected final Function<Field, String> valueFunction;

    public ValueMessage(int index) {
        this(record -> record.getFieldAt(index), DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(int index,
                        String nullFieldMessage,
                        Function<Field, String> valueFunction) {
        this(record -> record.getFieldAt(index), nullFieldMessage, valueFunction);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction) {
        this(fieldFunction, DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        String nullFieldMessage,
                        Function<Field, String> valueFunction) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(valueFunction);
        this.fieldFunction = fieldFunction;
        this.nullFieldMessage = nullFieldMessage;
        this.valueFunction = valueFunction;
    }

    public static <T extends KeyRecord> ValueMessage<T> key(FieldValueMapper valueMapper) {
        Objects.requireNonNull(valueMapper);
        return new ValueMessage<>(KeyRecord::getKeyField, DEFAULT_NULL_FIELD_MESSAGE, valueMapper.asFunction());
    }

    public static <T extends ValueRecord> ValueMessage<T> value(FieldValueMapper valueMapper) {
        Objects.requireNonNull(valueMapper);
        return new ValueMessage<>(ValueRecord::getValueField, DEFAULT_NULL_FIELD_MESSAGE, valueMapper.asFunction());
    }

    @Override
    public String createMessage(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldMessage;
        }
        return valueFunction.apply(field);
    }

}
