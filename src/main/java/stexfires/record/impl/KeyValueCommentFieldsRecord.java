package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.KeyValueCommentRecord;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record KeyValueCommentFieldsRecord(@Nullable String category, @Nullable Long recordId,
                                          @NotNull Field keyField, @NotNull Field valueField,
                                          @NotNull Field commentField)
        implements KeyValueCommentRecord, Serializable {

    public static final int KEY_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int COMMENT_INDEX = Fields.FIRST_FIELD_INDEX + 2;
    public static final int MAX_INDEX = COMMENT_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public KeyValueCommentFieldsRecord(@NotNull String key, @Nullable String value, @Nullable String comment) {
        this(null, null, key, value, comment);
    }

    public KeyValueCommentFieldsRecord(@Nullable String category, @Nullable Long recordId,
                                       @NotNull String key, @Nullable String value, @Nullable String comment) {
        this(category, recordId,
                new Field(KEY_INDEX, MAX_INDEX, key),
                new Field(VALUE_INDEX, MAX_INDEX, value),
                new Field(COMMENT_INDEX, MAX_INDEX, comment));
    }

    public KeyValueCommentFieldsRecord {
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
        // commentField
        Objects.requireNonNull(commentField);
        if (commentField.index() != COMMENT_INDEX) {
            throw new IllegalArgumentException("Wrong 'index' of commentField: " + commentField);
        }
        if (commentField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of commentField: " + commentField);
        }
    }

    @Override
    public @NotNull KeyValueCommentFieldsRecord withKey(@NotNull String key) {
        Objects.requireNonNull(key);
        return new KeyValueCommentFieldsRecord(category, recordId, key, value(), comment());
    }

    @Override
    public @NotNull KeyValueCommentFieldsRecord withValue(@Nullable String value) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value, comment());
    }

    @Override
    public @NotNull KeyValueCommentFieldsRecord withComment(@Nullable String comment) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value(), comment);
    }

    @Override
    public @NotNull KeyValueCommentFieldsRecord withKeyAndValue(@NotNull String key, @Nullable String value) {
        Objects.requireNonNull(key);
        return new KeyValueCommentFieldsRecord(category, recordId, key, value, comment());
    }

    @Override
    public @NotNull KeyValueCommentFieldsRecord withValueAndComment(@Nullable String value, @Nullable String comment) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value, comment);
    }

    @Override
    public @NotNull Field[] arrayOfFields() {
        return new Field[]{keyField, valueField, commentField};
    }

    @Override
    public @NotNull List<Field> listOfFields() {
        return List.of(keyField, valueField, commentField);
    }

    @Override
    public @NotNull List<Field> listOfFieldsReversed() {
        return List.of(commentField, valueField, keyField);
    }

    @Override
    public @NotNull Stream<Field> streamOfFields() {
        return Stream.of(keyField, valueField, commentField);
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
        return index == KEY_INDEX || index == VALUE_INDEX || index == COMMENT_INDEX;
    }

    @Override
    public @Nullable Field fieldAt(int index) {
        return switch (index) {
            case KEY_INDEX -> keyField;
            case VALUE_INDEX -> valueField;
            case COMMENT_INDEX -> commentField;
            default -> null;
        };
    }

    @Override
    public @NotNull Field firstField() {
        return keyField;
    }

    @Override
    public @NotNull Field lastField() {
        return commentField;
    }

    @Override
    public @NotNull Field keyField() {
        return keyField;
    }

    @Override
    public @NotNull Field valueField() {
        return valueField;
    }

    @Override
    public @NotNull Field commentField() {
        return commentField;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String firstText() {
        return keyField.text();
    }

    @Override
    public @Nullable String lastText() {
        return commentField.text();
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

    @Override
    public @Nullable String comment() {
        return commentField.text();
    }

}
