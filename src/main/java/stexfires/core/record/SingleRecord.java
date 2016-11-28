package stexfires.core.record;

import stexfires.core.Field;
import stexfires.core.Fields;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleRecord implements ValueRecord {

    public static final int VALUE_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int FIELD_SIZE = 1;

    private static final long serialVersionUID = 1L;

    private final String category;
    private final Long recordId;
    private final Field singleField;

    private final int hashCode;

    public SingleRecord(String value) {
        this(null, null, value);
    }

    public SingleRecord(String category, Long recordId, String value) {
        this.category = category;
        this.recordId = recordId;
        this.singleField = Fields.newArray(value)[VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, singleField);
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
        return Collections.singletonList(singleField);
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

    @Override
    public final Field getFieldAt(int index) {
        return (index == VALUE_INDEX) ? singleField : null;
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
        return (index == VALUE_INDEX) ? singleField.getValue() : null;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SingleRecord that = (SingleRecord) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(recordId, that.recordId) &&
                Objects.equals(singleField, that.singleField);
    }

    @Override
    public int hashCode() {
        return hashCode;
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
