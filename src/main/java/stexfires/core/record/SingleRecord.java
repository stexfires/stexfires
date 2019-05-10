package stexfires.core.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    private final String category;
    private final Long recordId;
    private final Field singleField;

    private final int hashCode;

    public SingleRecord(@Nullable String value) {
        this(null, null, value);
    }

    public SingleRecord(@Nullable String category, @Nullable Long recordId, @Nullable String value) {
        this.category = category;
        this.recordId = recordId;
        this.singleField = Fields.newArray(value)[VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, singleField);
    }

    @Override
    public SingleRecord newValueRecord(@Nullable String value) {
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
    public final @Nullable String getCategory() {
        return category;
    }

    @Override
    public final @Nullable Long getRecordId() {
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
    public final @Nullable Field getFieldAt(int index) {
        return (index == VALUE_INDEX) ? singleField : null;
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    @Override
    public final @NotNull Field getFirstField() {
        return singleField;
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    @Override
    public final @NotNull Field getLastField() {
        return singleField;
    }

    @SuppressWarnings("SuspiciousGetterSetter")
    @Override
    public final @NotNull Field getValueField() {
        return singleField;
    }

    @Override
    public final @Nullable String getValueAt(int index) {
        return (index == VALUE_INDEX) ? singleField.getValue() : null;
    }

    @Override
    public final @Nullable String getValueOfValueField() {
        return singleField.getValue();
    }

    @Override
    public final @Nullable String getValueOfFirstField() {
        return singleField.getValue();
    }

    @Override
    public final @Nullable String getValueOfLastField() {
        return singleField.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        SingleRecord record = (SingleRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Objects.equals(singleField, record.singleField);
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
