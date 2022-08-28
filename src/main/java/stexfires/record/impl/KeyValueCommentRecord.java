package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.CommentRecord;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.KeyRecord;
import stexfires.record.ValueRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class KeyValueCommentRecord implements KeyRecord, ValueRecord, CommentRecord {

    public static final int KEY_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int COMMENT_INDEX = Fields.FIRST_FIELD_INDEX + 2;
    public static final int FIELD_SIZE = 3;

    private final String category;
    private final Long recordId;
    private final Field keyField;
    private final Field valueField;
    private final Field commentField;

    private final int hashCode;

    public KeyValueCommentRecord(@NotNull String key, @Nullable String value, @Nullable String comment) {
        this(null, null, Objects.requireNonNull(key), value, comment);
    }

    public KeyValueCommentRecord(@Nullable String category, @Nullable Long recordId,
                                 @NotNull String key, @Nullable String value, @Nullable String comment) {
        Objects.requireNonNull(key);
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(key, value, comment);
        keyField = fields[KEY_INDEX];
        valueField = fields[VALUE_INDEX];
        commentField = fields[COMMENT_INDEX];

        hashCode = Objects.hash(category, recordId, keyField, valueField, commentField);
    }

    @Override
    public KeyValueCommentRecord withKey(@NotNull String key) {
        Objects.requireNonNull(key);
        return new KeyValueCommentRecord(category, recordId, key, valueField.value(), commentField.value());
    }

    @Override
    public KeyValueCommentRecord withValue(@Nullable String value) {
        return new KeyValueCommentRecord(category, recordId, keyField.value(), value, commentField.value());
    }

    @Override
    public KeyValueCommentRecord withComment(@Nullable String comment) {
        return new KeyValueCommentRecord(category, recordId, keyField.value(), valueField.value(), comment);
    }

    @Override
    public Field[] arrayOfFields() {
        return new Field[]{keyField, valueField, commentField};
    }

    @Override
    public List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(keyField);
        list.add(valueField);
        list.add(commentField);
        return list;
    }

    @Override
    public List<Field> listOfFieldsReversed() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(commentField);
        list.add(valueField);
        list.add(keyField);
        return list;
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Stream.of(keyField, valueField, commentField);
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
        return index == KEY_INDEX || index == VALUE_INDEX || index == COMMENT_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
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

    @Override
    public @NotNull String valueOfFirstField() {
        return keyField.value();
    }

    @Override
    public @Nullable String valueOfLastField() {
        return commentField.value();
    }

    @Override
    public @NotNull String valueOfKeyField() {
        return keyField.value();
    }

    @Override
    public @Nullable String valueOfValueField() {
        return valueField.value();
    }

    @Override
    public @Nullable String valueOfCommentField() {
        return commentField.value();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        KeyValueCommentRecord record = (KeyValueCommentRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Objects.equals(keyField, record.keyField) &&
                Objects.equals(valueField, record.valueField) &&
                Objects.equals(commentField, record.commentField);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "KeyValueCommentRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", key=" + keyField.value() +
                ", value=" + valueField.value() +
                ", comment=" + commentField.value() +
                '}';
    }

}
