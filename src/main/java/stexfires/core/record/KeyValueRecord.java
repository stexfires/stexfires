package stexfires.core.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.KeyRecord;
import stexfires.core.ValueRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class KeyValueRecord implements KeyRecord, ValueRecord {

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
    public final Field[] arrayOfFields() {
        return new Field[]{keyField, valueField};
    }

    @Override
    public final List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(keyField);
        list.add(valueField);
        return list;
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Stream.of(keyField, valueField);
    }

    @Override
    public final String category() {
        return category;
    }

    @Override
    public final Long recordId() {
        return recordId;
    }

    @Override
    public final int size() {
        return FIELD_SIZE;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean isValidIndex(int index) {
        return index == KEY_INDEX || index == VALUE_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final Field fieldAt(int index) {
        return switch (index) {
            case KEY_INDEX -> keyField;
            case VALUE_INDEX -> valueField;
            default -> null;
        };
    }

    @Override
    public final @NotNull Field firstField() {
        return keyField;
    }

    @Override
    public final @NotNull Field lastField() {
        return valueField;
    }

    @Override
    public final @NotNull Field keyField() {
        return keyField;
    }

    @Override
    public final @NotNull Field valueField() {
        return valueField;
    }

    @Override
    public final @NotNull String valueOfFirstField() {
        return keyField.value();
    }

    @Override
    public final @Nullable String valueOfLastField() {
        return valueField.value();
    }

    @Override
    public final @NotNull String valueOfKeyField() {
        return keyField.value();
    }

    @Override
    public final @Nullable String valueOfValueField() {
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
