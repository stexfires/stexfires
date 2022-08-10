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
        return (index == VALUE_INDEX) ? singleField : null;
    }

    @Override
    public final @NotNull Field firstField() {
        return singleField;
    }

    @Override
    public final @NotNull Field lastField() {
        return singleField;
    }

    @Override
    public final @NotNull Field valueField() {
        return singleField;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final String valueAt(int index) {
        return (index == VALUE_INDEX) ? singleField.value() : null;
    }

    @Override
    public final String valueOfValueField() {
        return singleField.value();
    }

    @Override
    public final String valueOfFirstField() {
        return singleField.value();
    }

    @Override
    public final String valueOfLastField() {
        return singleField.value();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
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
                ", value=" + singleField.value() +
                '}';
    }

}
