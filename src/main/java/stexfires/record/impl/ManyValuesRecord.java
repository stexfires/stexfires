package stexfires.record.impl;

import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ManyValuesRecord implements TextRecord {

    private final String category;
    private final Long recordId;
    private final Field[] fields;

    private final int hashCode;

    public ManyValuesRecord() {
        this(null, null, Fields.emptyArray());
    }

    public ManyValuesRecord(Collection<String> values) {
        this(null, null, Fields.newArray(values));
    }

    public ManyValuesRecord(@Nullable String category, Collection<String> values) {
        this(category, null, Fields.newArray(values));
    }

    public ManyValuesRecord(@Nullable String category, @Nullable Long recordId, Collection<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    public ManyValuesRecord(Stream<String> values) {
        this(null, null, Fields.newArray(values));
    }

    public ManyValuesRecord(@Nullable String category, Stream<String> values) {
        this(category, null, Fields.newArray(values));
    }

    public ManyValuesRecord(@Nullable String category, @Nullable Long recordId, Stream<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyValuesRecord(String... values) {
        this(null, null, Fields.newArray(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyValuesRecord(@Nullable String category, @Nullable Long recordId, String... values) {
        this(category, recordId, Fields.newArray(values));
    }

    @SuppressWarnings("MethodCanBeVariableArityMethod")
    private ManyValuesRecord(@Nullable String category, @Nullable Long recordId, Field[] fields) {
        this.category = category;
        this.recordId = recordId;
        this.fields = fields;

        hashCode = Objects.hash(category, recordId, Arrays.hashCode(fields));
    }

    @Override
    public Field[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public List<Field> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public List<Field> listOfFieldsReversed() {
        var fieldList = listOfFields();
        Collections.reverse(fieldList);
        return fieldList;
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Arrays.stream(arrayOfFields());
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
        return fields.length;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        ManyValuesRecord record = (ManyValuesRecord) obj;
        return Objects.equals(category, record.category) &&
                Objects.equals(recordId, record.recordId) &&
                Arrays.equals(fields, record.fields);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "ManyValuesRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", values=[" + Fields.joinValues(fields) +
                "]}";
    }

}
