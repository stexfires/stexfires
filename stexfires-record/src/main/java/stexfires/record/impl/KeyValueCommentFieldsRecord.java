package stexfires.record.impl;

import org.jspecify.annotations.Nullable;
import stexfires.record.KeyValueCommentRecord;
import stexfires.record.TextField;

import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public record KeyValueCommentFieldsRecord(@Nullable String category,
                                          @Nullable Long recordId,
                                          TextField keyField,
                                          TextField valueField,
                                          TextField commentField)
        implements KeyValueCommentRecord, Serializable {

    public static final int KEY_INDEX = TextField.FIRST_FIELD_INDEX;
    public static final int VALUE_INDEX = TextField.FIRST_FIELD_INDEX + 1;
    public static final int COMMENT_INDEX = TextField.FIRST_FIELD_INDEX + 2;
    public static final int MAX_INDEX = COMMENT_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public KeyValueCommentFieldsRecord(String key, @Nullable String value, @Nullable String comment) {
        this(null, null, key, value, comment);
    }

    public KeyValueCommentFieldsRecord(@Nullable String category,
                                       @Nullable Long recordId,
                                       String key,
                                       @Nullable String value,
                                       @Nullable String comment) {
        this(category,
                recordId,
                new TextField(KEY_INDEX, MAX_INDEX, key),
                new TextField(VALUE_INDEX, MAX_INDEX, value),
                new TextField(COMMENT_INDEX, MAX_INDEX, comment));
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
    public KeyValueCommentFieldsRecord withKey(String key) {
        Objects.requireNonNull(key);
        return new KeyValueCommentFieldsRecord(category, recordId, key, value(), comment());
    }

    @Override
    public KeyValueCommentFieldsRecord withValue(@Nullable String value) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value, comment());
    }

    @Override
    public KeyValueCommentFieldsRecord withComment(@Nullable String comment) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value(), comment);
    }

    @Override
    public KeyValueCommentFieldsRecord withKeyAndValue(String key, @Nullable String value) {
        Objects.requireNonNull(key);
        return new KeyValueCommentFieldsRecord(category, recordId, key, value, comment());
    }

    @Override
    public KeyValueCommentFieldsRecord withValueAndComment(@Nullable String value, @Nullable String comment) {
        return new KeyValueCommentFieldsRecord(category, recordId, key(), value, comment);
    }

    @Override
    public TextField[] arrayOfFields() {
        return new TextField[]{keyField, valueField, commentField};
    }

    @Override
    public List<TextField> listOfFields() {
        return List.of(keyField, valueField, commentField);
    }

    @Override
    public List<TextField> listOfFieldsReversed() {
        return List.of(commentField, valueField, keyField);
    }

    @Override
    public Stream<TextField> streamOfFields() {
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
    public @Nullable TextField fieldAt(int index) {
        return switch (index) {
            case KEY_INDEX -> keyField;
            case VALUE_INDEX -> valueField;
            case COMMENT_INDEX -> commentField;
            default -> null;
        };
    }

    @Override
    public TextField firstField() {
        return keyField;
    }

    @Override
    public TextField firstFieldOrElseThrow() {
        return keyField;
    }

    @Override
    public TextField lastField() {
        return commentField;
    }

    @Override
    public TextField lastFieldOrElseThrow() {
        return commentField;
    }

    @Override
    public TextField keyField() {
        return keyField;
    }

    @Override
    public TextField valueField() {
        return valueField;
    }

    @Override
    public TextField commentField() {
        return commentField;
    }

    @Override
    public String firstText() {
        return keyField.orElseThrow();
    }

    @Override
    public @Nullable String lastText() {
        return commentField.text();
    }

    @Override
    public String key() {
        return keyField.orElseThrow();
    }

    @Override
    public @Nullable String value() {
        return valueField.text();
    }

    @Override
    public @Nullable String comment() {
        return commentField.text();
    }

    @Override
    public int keyIndex() {
        return KEY_INDEX;
    }

    @Override
    public int valueIndex() {
        return VALUE_INDEX;
    }

    @Override
    public int commentIndex() {
        return COMMENT_INDEX;
    }

}
