package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
public final class KeyValueRecord implements KeyRecord, ValueRecord {

    public static final int KEY_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int FIELD_SIZE = 2;

    private final String category;
    private final Long recordId;
    private final Field keyField;
    private final Field valueField;

    private final int hashCode;

    public KeyValueRecord(@NotNull String key, @Nullable String value) {
        this(null, null, Objects.requireNonNull(key), value);
    }

    public KeyValueRecord(@Nullable String category, @Nullable Long recordId,
                          @NotNull String key, @Nullable String value) {
        Objects.requireNonNull(key);
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(key, value);
        keyField = fields[KEY_INDEX];
        valueField = fields[VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, keyField, valueField);
    }

    @Override
    public KeyValueRecord newKeyRecord(@NotNull String key) {
        Objects.requireNonNull(key);
        return new KeyValueRecord(category, recordId, key, valueField.value());
    }

    @Override
    public KeyValueRecord newValueRecord(@Nullable String value) {
        return new KeyValueRecord(category, recordId, keyField.value(), value);
    }

    @Override
    public Field[] arrayOfFields() {
        return new Field[]{keyField, valueField};
    }

    @Override
    public List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(keyField);
        list.add(valueField);
        return list;
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Stream.of(keyField, valueField);
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
        return index == KEY_INDEX || index == VALUE_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
        return switch (index) {
            case KEY_INDEX -> keyField;
            case VALUE_INDEX -> valueField;
            default -> null;
        };
    }

    @Override
    public @NotNull Field firstField() {
        return keyField;
    }

    @Override
    public @NotNull Field lastField() {
        return valueField;
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
    public @NotNull String valueOfFirstField() {
        return keyField.value();
    }

    @Override
    public @Nullable String valueOfLastField() {
        return valueField.value();
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
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        KeyValueRecord record = (KeyValueRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Objects.equals(keyField, record.keyField) &&
                Objects.equals(valueField, record.valueField);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "KeyValueRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", key=" + keyField.value() +
                ", value=" + valueField.value() +
                '}';
    }

}
