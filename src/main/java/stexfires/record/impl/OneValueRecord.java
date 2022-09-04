package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.ValueRecord;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record OneValueRecord(@Nullable String category, @Nullable Long recordId, @NotNull Field valueField)
        implements ValueRecord, Serializable {

    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int MAX_INDEX = VALUE_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public OneValueRecord(@Nullable String value) {
        this(null, null, value);
    }

    public OneValueRecord(@Nullable String category, @Nullable Long recordId, @Nullable String value) {
        this(category, recordId,
                new Field(VALUE_INDEX, MAX_INDEX, value));
    }

    public OneValueRecord {
        // valueField
        Objects.requireNonNull(valueField);
        if (valueField.index() != VALUE_INDEX) {
            throw new IllegalArgumentException("Wrong 'index' of valueField: " + valueField);
        }
        if (valueField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of valueField: " + valueField);
        }
    }

    @Override
    public OneValueRecord withValue(@Nullable String value) {
        return new OneValueRecord(category, recordId, value);
    }

    @Override
    public Field[] arrayOfFields() {
        return new Field[]{valueField};
    }

    @Override
    public List<Field> listOfFields() {
        return Collections.singletonList(valueField);
    }

    @Override
    public List<Field> listOfFieldsReversed() {
        return Collections.singletonList(valueField);
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Stream.of(valueField);
    }

    @Override
    public String category() {
        return category;
    }

    @Override
    public Long recordId() {
        return recordId;
    }

    @Override
    public int size() {
        return FIELD_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isValidIndex(int index) {
        return index == VALUE_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
        return (index == VALUE_INDEX) ? valueField : null;
    }

    @Override
    public @NotNull Field firstField() {
        return valueField;
    }

    @Override
    public @NotNull Field lastField() {
        return valueField;
    }

    @Override
    public @NotNull Field valueField() {
        return valueField;
    }

    @Override
    public @Nullable String valueOfFirstField() {
        return valueField.text();
    }

    @Override
    public @Nullable String valueOfLastField() {
        return valueField.text();
    }

    @Override
    public @Nullable String value() {
        return valueField.text();
    }

}
