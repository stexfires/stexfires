package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.io.Serializable;
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
public record StandardRecord(@Nullable String category, @Nullable Long recordId,
                             @NotNull Field[] fields)
        implements TextRecord, Serializable {

    public StandardRecord() {
        this(null, null, Fields.emptyArray());
    }

    public StandardRecord(Collection<String> texts) {
        this(null, null, Fields.newArray(texts));
    }

    public StandardRecord(@Nullable String category, Collection<String> texts) {
        this(category, null, Fields.newArray(texts));
    }

    public StandardRecord(@Nullable String category, @Nullable Long recordId, Collection<String> texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    public StandardRecord(Stream<String> texts) {
        this(null, null, Fields.newArray(texts));
    }

    public StandardRecord(@Nullable String category, Stream<String> texts) {
        this(category, null, Fields.newArray(texts));
    }

    public StandardRecord(@Nullable String category, @Nullable Long recordId, Stream<String> texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public StandardRecord(String... texts) {
        this(null, null, Fields.newArray(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public StandardRecord(@Nullable String category, @Nullable Long recordId, String... texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    public StandardRecord(@Nullable String category, @Nullable Long recordId, @NotNull Field[] fields) {
        Objects.requireNonNull(fields);
        this.category = category;
        this.recordId = recordId;

        // Check and copy fields
        this.fields = new Field[fields.length];
        int maxIndex = fields.length - 1;
        for (int index = Fields.FIRST_FIELD_INDEX; index < fields.length; index++) {
            var field = fields[index];
            if (field.index() != index) {
                throw new IllegalArgumentException("Wrong 'index' of field: " + field);
            }
            if (field.maxIndex() != maxIndex) {
                throw new IllegalArgumentException("Wrong 'maxIndex' of field: " + field);
            }

            this.fields[index] = field;
        }
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

}
