package stexfires.core.message;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.record.KeyRecord;
import stexfires.core.record.ValueRecord;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMessage<T extends TextRecord> implements RecordMessage<T> {

    public static final String DEFAULT_NULL_FIELD_MESSAGE = null;

    private final Function<? super T, Field> fieldFunction;
    private final String nullFieldMessage;
    private final FieldValueMapper fieldValueMapper;

    public ValueMessage(int index) {
        this(record -> record.getFieldAt(index), DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(int index,
                        @Nullable String nullFieldMessage) {
        this(record -> record.getFieldAt(index), nullFieldMessage, Field::getValue);
    }

    public ValueMessage(int index,
                        @Nullable String nullFieldMessage,
                        FieldValueMapper fieldValueMapper) {
        this(record -> record.getFieldAt(index), nullFieldMessage, fieldValueMapper);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction) {
        this(fieldFunction, DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        @Nullable String nullFieldMessage) {
        this(fieldFunction, nullFieldMessage, Field::getValue);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        @Nullable String nullFieldMessage,
                        FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldValueMapper);
        this.fieldFunction = fieldFunction;
        this.nullFieldMessage = nullFieldMessage;
        this.fieldValueMapper = fieldValueMapper;
    }

    public static <T extends KeyRecord> ValueMessage<T> key() {
        return new ValueMessage<>(KeyRecord::getKeyField, DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public static <T extends KeyRecord> ValueMessage<T> keyField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(KeyRecord::getKeyField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
    }

    public static <T extends ValueRecord> ValueMessage<T> value() {
        return new ValueMessage<>(ValueRecord::getValueField, DEFAULT_NULL_FIELD_MESSAGE, Field::getValue);
    }

    public static <T extends ValueRecord> ValueMessage<T> valueField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(ValueRecord::getValueField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
    }

    @Override
    public final @Nullable String createMessage(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldMessage;
        }
        return fieldValueMapper.mapToValue(field);
    }

}
