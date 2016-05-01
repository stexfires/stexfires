package org.textfiledatatools.core.record;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.Records;

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

    public StandardRecord() {
        this(null, null, 0);
    }

    public StandardRecord(Collection<String> values) {
        this(null, null, values);
    }

    public StandardRecord(String category, Long recordId, Collection<String> values) {
        this(category, recordId, values.size());
        int index = 0;
        for (String value : values) {
            fields[index] = new Field(index, index + 1 == values.size(), value);
            index++;
        }
    }

    public StandardRecord(String... values) {
        this(null, null, values);
    }

    public StandardRecord(String category, Long recordId, String... values) {
        this(category, recordId, values.length);
        for (int index = 0; index < values.length; index++) {
            fields[index] = new Field(index, index + 1 == values.length, values[index]);
        }
    }

    private StandardRecord(String category, Long recordId, int size) {
        this.category = category;
        this.recordId = recordId;
        fields = new Field[size];
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
                Arrays.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, fields);
    }

    @Override
    public String toString() {
        return "StandardRecord{" +
                "category=" + category +
                ", recordId=" + recordId +
                ", values=[" + Records.joinFieldValues(fields) +
                "]}";
    }
}
