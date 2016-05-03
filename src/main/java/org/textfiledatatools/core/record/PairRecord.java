package org.textfiledatatools.core.record;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Fields;
import org.textfiledatatools.core.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PairRecord implements Record {

    private static final long serialVersionUID = 1L;

    private final String category;
    private final Long recordId;
    private final Field firstField;
    private final Field secondField;

    public PairRecord(String firstValue, String secondValue) {
        this(null, null, firstValue, secondValue);
    }

    public PairRecord(String category, Long recordId, String firstValue, String secondValue) {
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(firstValue, secondValue);
        firstField = fields[0];
        secondField = fields[1];
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
        List<Field> list = new ArrayList<>(2);
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
        return 2;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean isValidIndex(int index) {
        return index == 0 || index == 1;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairRecord that = (PairRecord) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(firstField, that.firstField) &&
                Objects.equals(secondField, that.secondField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, firstField, secondField);
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
