package stexfires.core.record;

import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PairRecord implements Record {

    public static final int FIRST_VALUE_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int SECOND_VALUE_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int FIELD_SIZE = 2;

    private final String category;
    private final Long recordId;
    private final Field firstField;
    private final Field secondField;

    private final int hashCode;

    public PairRecord(String firstValue, String secondValue) {
        this(null, null, firstValue, secondValue);
    }

    public PairRecord(String category, Long recordId, String firstValue, String secondValue) {
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(firstValue, secondValue);
        firstField = fields[FIRST_VALUE_INDEX];
        secondField = fields[SECOND_VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, firstField, secondField);
    }

    public PairRecord newRecordSwapped() {
        return new PairRecord(category, recordId, secondField.getValue(), firstField.getValue());
    }

    @Override
    public final Field[] arrayOfFields() {
        return new Field[]{firstField, secondField};
    }

    @Override
    public final List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(firstField);
        list.add(secondField);
        return list;
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Stream.of(firstField, secondField);
    }

    @Override
    public final String getCategory() {
        return category;
    }

    @Override
    public final Long getRecordId() {
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
        return index == FIRST_VALUE_INDEX || index == SECOND_VALUE_INDEX;
    }

    @Override
    public final Field getFieldAt(int index) {
        switch (index) {
            case 0:
                return firstField;
            case 1:
                return secondField;
            default:
                return null;
        }
    }

    @Override
    public final Field getFirstField() {
        return firstField;
    }

    @Override
    public final Field getLastField() {
        return secondField;
    }

    public final Field getSecondField() {
        return secondField;
    }

    @Override
    public final String getValueOfFirstField() {
        return firstField.getValue();
    }

    @Override
    public final String getValueOfLastField() {
        return secondField.getValue();
    }

    public final String getValueOfSecondField() {
        return secondField.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        PairRecord record = (PairRecord) obj;
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
        return "PairRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", firstValue=" + firstField.getValue() +
                ", secondValue=" + secondField.getValue() +
                '}';
    }

}
