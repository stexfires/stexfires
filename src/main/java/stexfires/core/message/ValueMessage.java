package stexfires.core.message;

import stexfires.core.Field;
import stexfires.core.Record;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ValueMessage<T extends Record> implements RecordMessage<T> {

    public static final String DEFAULT_NULL_FIELD_VALUE = null;

    protected final Function<? super T, Field> fieldFunction;
    protected final String nullFieldValue;

    public ValueMessage(int index) {
        this(record -> record.getFieldAt(index), DEFAULT_NULL_FIELD_VALUE);
    }

    public ValueMessage(int index,
                        String nullFieldValue) {
        this(record -> record.getFieldAt(index), nullFieldValue);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction) {
        this(fieldFunction, null);
    }

    public ValueMessage(Function<? super T, Field> fieldFunction,
                        String nullFieldValue) {
        Objects.requireNonNull(fieldFunction);
        this.fieldFunction = fieldFunction;
        this.nullFieldValue = nullFieldValue;
    }

    @Override
    public String createMessage(T record) {
        Field field = fieldFunction.apply(record);
        if (field == null) {
            return nullFieldValue;
        }
        return field.getValue();
    }

}
