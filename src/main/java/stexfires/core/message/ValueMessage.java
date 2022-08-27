package stexfires.core.message;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.TextRecord;
import stexfires.core.mapper.fieldvalue.FieldValueMapper;
import stexfires.core.KeyRecord;
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
        this(record -> record.fieldAt(index), DEFAULT_NULL_FIELD_MESSAGE, Field::value);
    }

    public ValueMessage(int index,
                        @Nullable String nullFieldMessage) {
        this(record -> record.fieldAt(index), nullFieldMessage, Field::value);
    }

    public ValueMessage(int index,
                        @Nullable String nullFieldMessage,
                        FieldValueMapper fieldValueMapper) {
        this(record -> record.fieldAt(index), nullFieldMessage, fieldValueMapper);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction) {
        this(fieldFunction, DEFAULT_NULL_FIELD_MESSAGE, Field::value);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        @Nullable String nullFieldMessage) {
        this(fieldFunction, nullFieldMessage, Field::value);
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
        return new ValueMessage<>(KeyRecord::keyField, DEFAULT_NULL_FIELD_MESSAGE, Field::value);
    }

    public static <T extends KeyRecord> ValueMessage<T> keyField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(KeyRecord::keyField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
    }

    public static <T extends ValueRecord> ValueMessage<T> value() {
        return new ValueMessage<>(ValueRecord::valueField, DEFAULT_NULL_FIELD_MESSAGE, Field::value);
    }

    public static <T extends ValueRecord> ValueMessage<T> valueField(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return new ValueMessage<>(ValueRecord::valueField, DEFAULT_NULL_FIELD_MESSAGE, fieldValueMapper);
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
