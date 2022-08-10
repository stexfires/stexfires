package stexfires.core.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.TextRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class PairRecord implements TextRecord {

    public static final int FIRST_VALUE_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int SECOND_VALUE_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int FIELD_SIZE = 2;

    private final String category;
    private final Long recordId;
    private final Field firstField;
    private final Field secondField;

    private final int hashCode;

    public PairRecord(@Nullable String firstValue, @Nullable String secondValue) {
        this(null, null, firstValue, secondValue);
    }

    public PairRecord(@Nullable String category, @Nullable Long recordId,
                      @Nullable String firstValue, @Nullable String secondValue) {
        this.category = category;
        this.recordId = recordId;
        Field[] fields = Fields.newArray(firstValue, secondValue);
        firstField = fields[FIRST_VALUE_INDEX];
        secondField = fields[SECOND_VALUE_INDEX];

        hashCode = Objects.hash(category, recordId, firstField, secondField);
    }

    public PairRecord newRecordSwapped() {
        return new PairRecord(category, recordId, secondField.value(), firstField.value());
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
        return index == FIRST_VALUE_INDEX || index == SECOND_VALUE_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final Field fieldAt(int index) {
        return switch (index) {
            case FIRST_VALUE_INDEX -> firstField;
            case SECOND_VALUE_INDEX -> secondField;
            default -> null;
        };
    }

    @Override
    public final @NotNull Field firstField() {
        return firstField;
    }

    @Override
    public final @NotNull Field lastField() {
        return secondField;
    }

    public final @NotNull Field secondField() {
        return secondField;
    }

    @Override
    public final String valueOfFirstField() {
        return firstField.value();
    }

    @Override
    public final String valueOfLastField() {
        return secondField.value();
    }

    public final String valueOfSecondField() {
        return secondField.value();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
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
                ", firstValue=" + firstField.value() +
                ", secondValue=" + secondField.value() +
                '}';
    }

}
