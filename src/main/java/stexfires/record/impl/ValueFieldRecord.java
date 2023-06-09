package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.ValueRecord;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public record ValueFieldRecord(@Nullable String category, @Nullable Long recordId,
                               @NotNull TextField valueField)
        implements ValueRecord, Serializable {

    public static final int VALUE_INDEX = TextFields.FIRST_FIELD_INDEX;
    public static final int MAX_INDEX = VALUE_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public ValueFieldRecord(@Nullable String value) {
        this(null, null, value);
    }

    public ValueFieldRecord(@Nullable String category, @Nullable Long recordId, @Nullable String value) {
        this(category, recordId,
                new TextField(VALUE_INDEX, MAX_INDEX, value));
    }

    public ValueFieldRecord {
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
    public @NotNull ValueFieldRecord withValue(@Nullable String value) {
        return new ValueFieldRecord(category, recordId, value);
    }

    @Override
    public @NotNull TextField[] arrayOfFields() {
        return new TextField[]{valueField};
    }

    @Override
    public @NotNull List<TextField> listOfFields() {
        return List.of(valueField);
    }

    @Override
    public @NotNull List<TextField> listOfFieldsReversed() {
        return List.of(valueField);
    }

    @Override
    public @NotNull Stream<TextField> streamOfFields() {
        return Stream.of(valueField);
    }

    @Override
    public @Nullable String category() {
        return category;
    }

    @Override
    public @Nullable Long recordId() {
        return recordId;
    }

    @Override
    public int size() {
        return FIELD_SIZE;
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isValidIndex(int index) {
        return index == VALUE_INDEX;
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return (index == VALUE_INDEX) ? valueField : null;
    }

    @Override
    public @NotNull TextField firstField() {
        return valueField;
    }

    @Override
    public @NotNull TextField lastField() {
        return valueField;
    }

    @Override
    public @NotNull TextField valueField() {
        return valueField;
    }

    @Override
    public int valueIndex() {
        return VALUE_INDEX;
    }

    @Override
    public @Nullable String firstText() {
        return valueField.text();
    }

    @Override
    public @Nullable String lastText() {
        return valueField.text();
    }

    @Override
    public @Nullable String value() {
        return valueField.text();
    }

}
