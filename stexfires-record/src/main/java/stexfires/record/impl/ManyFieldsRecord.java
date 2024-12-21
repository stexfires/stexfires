package stexfires.record.impl;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public record ManyFieldsRecord(@Nullable String category,
                               @Nullable Long recordId,
                               TextField[] fields)
        implements TextRecord, Serializable {

    public ManyFieldsRecord() {
        this(null, null, TextFields.emptyArray());
    }

    public ManyFieldsRecord(Collection<@Nullable String> texts) {
        this(null, null, TextFields.newArrayOfCollection(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, Collection<@Nullable String> texts) {
        this(category, recordId, TextFields.newArrayOfCollection(texts));
    }

    public ManyFieldsRecord(Stream<@Nullable String> texts) {
        this(null, null, TextFields.newArrayOfStream(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, Stream<@Nullable String> texts) {
        this(category, recordId, TextFields.newArrayOfStream(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyFieldsRecord(@Nullable String... texts) {
        this(null, null, TextFields.newArrayOfStrings(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @Nullable String... texts) {
        this(category, recordId, TextFields.newArrayOfStrings(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, TextField[] fields) {
        Objects.requireNonNull(fields);
        this.category = category;
        this.recordId = recordId;

        // Check and copy fields
        this.fields = new TextField[fields.length];
        int maxIndex = fields.length - 1;
        for (int index = TextField.FIRST_FIELD_INDEX; index < fields.length; index++) {
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
    public TextField[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public List<TextField> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public List<TextField> listOfFieldsReversed() {
        var fieldList = listOfFields();
        Collections.reverse(fieldList);
        return fieldList;
    }

    @Override
    public Stream<TextField> streamOfFields() {
        return Arrays.stream(arrayOfFields());
    }

    @Override
    public @Nullable String category() {
        return category;
    }

    @Override
    public @Nullable Long recordId() {
        return recordId;
    }

    @Override
    public int size() {
        return fields.length;
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

}
