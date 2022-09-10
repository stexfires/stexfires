package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.KeyValueRecord;
import stexfires.record.TextField;
import stexfires.record.TextFields;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record KeyValueFieldsRecord(@Nullable String category, @Nullable Long recordId,
                                   @NotNull TextField keyField, @NotNull TextField valueField)
        implements KeyValueRecord, Serializable {

    public static final int KEY_INDEX = TextFields.FIRST_FIELD_INDEX;
    public static final int VALUE_INDEX = TextFields.FIRST_FIELD_INDEX + 1;
    public static final int MAX_INDEX = VALUE_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public KeyValueFieldsRecord(@NotNull String key, @Nullable String value) {
        this(null, null, key, value);
    }

    public KeyValueFieldsRecord(@Nullable String category, @Nullable Long recordId,
                                @NotNull String key, @Nullable String value) {
        this(category, recordId,
                new TextField(KEY_INDEX, MAX_INDEX, key),
                new TextField(VALUE_INDEX, MAX_INDEX, value));
    }

    public KeyValueFieldsRecord {
        // keyField
        Objects.requireNonNull(keyField);
        if (keyField.index() != KEY_INDEX) {
            throw new IllegalArgumentException("Wrong 'index' of keyField: " + keyField);
        }
        if (keyField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of keyField: " + keyField);
        }
        if (keyField.isNull()) {
            throw new IllegalArgumentException("Wrong 'text' of keyField: " + keyField);
        }
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
    public @NotNull KeyValueFieldsRecord withKey(@NotNull String key) {
        Objects.requireNonNull(key);
        return new KeyValueFieldsRecord(category, recordId, key, value());
    }

    @Override
    public @NotNull KeyValueFieldsRecord withValue(@Nullable String value) {
        return new KeyValueFieldsRecord(category, recordId, key(), value);
    }

    @Override
    public @NotNull KeyValueFieldsRecord withKeyAndValue(@NotNull String key, @Nullable String value) {
        Objects.requireNonNull(key);
        return new KeyValueFieldsRecord(category, recordId, key, value);
    }

    @Override
    public @NotNull TextField[] arrayOfFields() {
        return new TextField[]{keyField, valueField};
    }

    @Override
    public @NotNull List<TextField> listOfFields() {
        return List.of(keyField, valueField);
    }

    @Override
    public @NotNull List<TextField> listOfFieldsReversed() {
        return List.of(valueField, keyField);
    }

    @Override
    public @NotNull Stream<TextField> streamOfFields() {
        return Stream.of(keyField, valueField);
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
        return index == KEY_INDEX || index == VALUE_INDEX;
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return switch (index) {
            case KEY_INDEX -> keyField;
            case VALUE_INDEX -> valueField;
            default -> null;
        };
    }

    @Override
    public @NotNull TextField firstField() {
        return keyField;
    }

    @Override
    public @NotNull TextField lastField() {
        return valueField;
    }

    @Override
    public @NotNull TextField keyField() {
        return keyField;
    }

    @Override
    public @NotNull TextField valueField() {
        return valueField;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String firstText() {
        return keyField.text();
    }

    @Override
    public @Nullable String lastText() {
        return valueField.text();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String key() {
        return keyField.text();
    }

    @Override
    public @Nullable String value() {
        return valueField.text();
    }

}
