package stexfires.core.record;

import org.jetbrains.annotations.Nullable;
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

    public StandardRecord(@Nullable String category, Collection<String> values) {
        this(category, null, Fields.newArray(values));
    }

    public StandardRecord(@Nullable String category, @Nullable Long recordId, Collection<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    public StandardRecord(Stream<String> values) {
        this(null, null, Fields.newArray(values));
    }

    public StandardRecord(@Nullable String category, Stream<String> values) {
        this(category, null, Fields.newArray(values));
    }

    public StandardRecord(@Nullable String category, @Nullable Long recordId, Stream<String> values) {
        this(category, recordId, Fields.newArray(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public StandardRecord(String... values) {
        this(null, null, Fields.newArray(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public StandardRecord(@Nullable String category, @Nullable Long recordId, String... values) {
        this(category, recordId, Fields.newArray(values));
    }

    private StandardRecord(@Nullable String category, @Nullable Long recordId, Field[] fields) {
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

    @SuppressWarnings("ReturnOfNull")
    @Override
    public final Field getFieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        StandardRecord record = (StandardRecord) obj;
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
        return "StandardRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", values=[" + Fields.joinValues(fields) +
                "]}";
    }

}
