package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.ValueRecord;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class OneValueRecord implements ValueRecord {

    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int FIELD_SIZE = 1;

    private final String category;
    private final Long recordId;
    private final Field valueField;

    private final int hashCode;

    public OneValueRecord(@Nullable String value) {
        this(null, null, value);
    }

    public OneValueRecord(@Nullable String category, @Nullable Long recordId, @Nullable String value) {
        this.category = category;
        this.recordId = recordId;
        this.valueField = Fields.newArray(value)[VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, valueField);
    }

    @Override
    public OneValueRecord newValueRecord(@Nullable String value) {
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
        return valueField.value();
    }

    @Override
    public @Nullable String valueOfLastField() {
        return valueField.value();
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

        OneValueRecord record = (OneValueRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Objects.equals(valueField, record.valueField);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "OneValueRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", value=" + valueField.value() +
                '}';
    }

}
