package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class TwoValuesRecord implements TextRecord {

    public static final int FIRST_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int SECOND_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int FIELD_SIZE = 2;

    private final String category;
    private final Long recordId;
    private final Field firstField;
    private final Field secondField;

    private final int hashCode;

    public TwoValuesRecord(@Nullable String firstValue, @Nullable String secondValue) {
        this(null, null, firstValue, secondValue);
    }

    public TwoValuesRecord(@Nullable String category, @Nullable Long recordId,
                           @Nullable String firstValue, @Nullable String secondValue) {
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(firstValue, secondValue);
        firstField = fields[FIRST_INDEX];
        secondField = fields[SECOND_INDEX];

        hashCode = Objects.hash(category, recordId, firstField, secondField);
    }

    public TwoValuesRecord newRecordSwapped() {
        return new TwoValuesRecord(category, recordId, secondField.value(), firstField.value());
    }

    @Override
    public Field[] arrayOfFields() {
        return new Field[]{firstField, secondField};
    }

    @Override
    public List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(firstField);
        list.add(secondField);
        return list;
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Stream.of(firstField, secondField);
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
        return index == FIRST_INDEX || index == SECOND_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
        return switch (index) {
            case FIRST_INDEX -> firstField;
            case SECOND_INDEX -> secondField;
            default -> null;
        };
    }

    @Override
    public @NotNull Field firstField() {
        return firstField;
    }

    @Override
    public @NotNull Field lastField() {
        return secondField;
    }

    public @NotNull Field secondField() {
        return secondField;
    }

    @Override
    public @Nullable String valueOfFirstField() {
        return firstField.value();
    }

    @Override
    public @Nullable String valueOfLastField() {
        return secondField.value();
    }

    public @Nullable String valueOfSecondField() {
        return secondField.value();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        TwoValuesRecord record = (TwoValuesRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Objects.equals(firstField, record.firstField) &&
                Objects.equals(secondField, record.secondField);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "TwoValuesRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", firstValue=" + firstField.value() +
                ", secondValue=" + secondField.value() +
                '}';
    }

}
