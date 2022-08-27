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
public class OneValueRecord implements ValueRecord {

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
    public final Field[] arrayOfFields() {
        return new Field[]{valueField};
    }

    @Override
    public final List<Field> listOfFields() {
        return Collections.singletonList(valueField);
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Stream.of(valueField);
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
        return index == VALUE_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final Field fieldAt(int index) {
        return (index == VALUE_INDEX) ? valueField : null;
    }

    @Override
    public final @NotNull Field firstField() {
        return valueField;
    }

    @Override
    public final @NotNull Field lastField() {
        return valueField;
    }

    @Override
    public final @NotNull Field valueField() {
        return valueField;
    }

    @Override
    public final @Nullable String valueOfFirstField() {
        return valueField.value();
    }

    @Override
    public final @Nullable String valueOfLastField() {
        return valueField.value();
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
