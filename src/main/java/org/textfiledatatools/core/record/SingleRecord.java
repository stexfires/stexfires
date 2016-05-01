package org.textfiledatatools.core.record;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Records;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleRecord implements ValueRecord {

    private static final long serialVersionUID = 1L;

    private final String category;
    private final Long recordId;
    private final Field singleField;

    public SingleRecord(String value) {
        this(null, null, value);
    }

    public SingleRecord(String category, Long recordId, String value) {
        this.category = category;
        this.recordId = recordId;
        this.singleField = new Field(Records.FIRST_FIELD_INDEX, true, value);
    }

    @Override
    public SingleRecord newValueRecord(String value) {
        return new SingleRecord(category, recordId, value);
    }

    @Override
    public final Field[] arrayOfFields() {
        return new Field[]{singleField};
    }

    @Override
    public final List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(1);
        list.add(singleField);
        return list;
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Stream.of(singleField);
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
        return 1;
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public final boolean isValidIndex(int index) {
        return index == 0;
    }

    @Override
    public final Field getFieldAt(int index) {
        return (index == 0) ? singleField : null;
    }

    @Override
    public final Field getFirstField() {
        return singleField;
    }

    @Override
    public final Field getLastField() {
        return singleField;
    }

    @Override
    public final Field getValueField() {
        return singleField;
    }

    @Override
    public final String getValueAt(int index) {
        return (index == 0) ? singleField.getValue() : null;
    }

    @Override
    public final String getValueOfValueField() {
        return singleField.getValue();
    }

    @Override
    public final String getValueOfFirstField() {
        return singleField.getValue();
    }

    @Override
    public final String getValueOfLastField() {
        return singleField.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleRecord that = (SingleRecord) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(singleField, that.singleField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, singleField);
    }

    @Override
    public String toString() {
        return "SingleRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", value=" + singleField.getValue() +
                '}';
    }

}
