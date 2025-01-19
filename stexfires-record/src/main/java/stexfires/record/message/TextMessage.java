package stexfires.record.message;

import org.jspecify.annotations.Nullable;
import stexfires.record.*;
import stexfires.record.mapper.field.FieldTextMapper;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class TextMessage<T extends TextRecord> implements RecordMessage<T> {

    public static final @Nullable String DEFAULT_NULL_FIELD_MESSAGE = null;

    private final Function<? super T, @Nullable TextField> fieldFunction;
    private final @Nullable String nullFieldMessage;
    private final FieldTextMapper fieldTextMapper;

    public TextMessage(int index) {
        this(record -> record.fieldAt(index), DEFAULT_NULL_FIELD_MESSAGE, TextField::text);
    }

    public TextMessage(int index,
                       @Nullable String nullFieldMessage) {
        this(record -> record.fieldAt(index), nullFieldMessage, TextField::text);
    }

    public TextMessage(int index,
                       @Nullable String nullFieldMessage,
                       FieldTextMapper fieldTextMapper) {
        this(record -> record.fieldAt(index), nullFieldMessage, fieldTextMapper);
    }

    public TextMessage(Function<? super T, @Nullable TextField> fieldFunction) {
        this(fieldFunction, DEFAULT_NULL_FIELD_MESSAGE, TextField::text);
    }

    public TextMessage(Function<? super T, @Nullable TextField> fieldFunction,
                       @Nullable String nullFieldMessage) {
        this(fieldFunction, nullFieldMessage, TextField::text);
    }

    public TextMessage(Function<? super T, @Nullable TextField> fieldFunction,
                       @Nullable String nullFieldMessage,
                       FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldFunction);
        Objects.requireNonNull(fieldTextMapper);
        this.fieldFunction = fieldFunction;
        this.nullFieldMessage = nullFieldMessage;
        this.fieldTextMapper = fieldTextMapper;
    }

    public static <T extends KeyRecord> TextMessage<T> key() {
        return new TextMessage<>(KeyRecord::keyField, DEFAULT_NULL_FIELD_MESSAGE, TextField::text);
    }

    public static <T extends KeyRecord> TextMessage<T> keyField(FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return new TextMessage<>(KeyRecord::keyField, DEFAULT_NULL_FIELD_MESSAGE, fieldTextMapper);
    }

    public static <T extends ValueRecord> TextMessage<T> value() {
        return new TextMessage<>(ValueRecord::valueField, DEFAULT_NULL_FIELD_MESSAGE, TextField::text);
    }

    public static <T extends ValueRecord> TextMessage<T> valueField(FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return new TextMessage<>(ValueRecord::valueField, DEFAULT_NULL_FIELD_MESSAGE, fieldTextMapper);
    }

    @Override
    public final @Nullable String createMessage(T record) {
        TextField field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldMessage;
        }
        return fieldTextMapper.mapToText(field);
    }

}
