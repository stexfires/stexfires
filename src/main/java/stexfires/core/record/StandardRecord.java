package stexfires.core.record;

import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.Record;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StandardRecord implements Record {

    private static final long serialVersionUID = 1L;

    private final String category;
    private final Long recordId;
    private final Field[] fields;

    private final int hashCode;

    public StandardRecord() {
        this(null, null, Fields.emptyArray());
    }

    public StandardRecord(Collection<String> values) {
        this(null, null, Fields.newArray(values));
    }

    public StandardRecord(String category, Long recordId, Collection<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    public StandardRecord(Stream<String> values) {
        this(null, null, Fields.newArray(values));
    }

    public StandardRecord(String category, Long recordId, Stream<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    public StandardRecord(String... values) {
        this(null, null, Fields.newArray(values));
    }

    public StandardRecord(String category, Long recordId, String... values) {
        this(category, recordId, Fields.newArray(values));
    }

    private StandardRecord(String category, Long recordId, Field[] fields) {
        this.category = category;
        this.recordId = recordId;
        this.fields = fields;

        hashCode = Objects.hash(category, recordId, fields);
    }

    @Override
    public final Field[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public final List<Field> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public final Stream<Field> streamOfFields() {
        return Arrays.stream(arrayOfFields());
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
        return fields.length;
    }

    @Override
    public final Field getFieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardRecord that = (StandardRecord) o;
        return Objects.equals(category, that.category) &&
                Objects.equals(recordId, that.recordId) &&
                Arrays.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "StandardRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", values=[" + Fields.joinValues(fields) +
                "]}";
    }

}
