package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
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
public record ManyFieldsRecord(@Nullable String category, @Nullable Long recordId,
                               @NotNull TextField[] fields)
        implements TextRecord, Serializable {

    public ManyFieldsRecord() {
        this(null, null, TextFields.emptyArray());
    }

    public ManyFieldsRecord(@NotNull Collection<String> texts) {
        this(null, null, TextFields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull Collection<String> texts) {
        this(category, recordId, TextFields.newArray(texts));
    }

    public ManyFieldsRecord(@NotNull Stream<String> texts) {
        this(null, null, TextFields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull Stream<String> texts) {
        this(category, recordId, TextFields.newArray(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyFieldsRecord(String... texts) {
        this(null, null, TextFields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, String... texts) {
        this(category, recordId, TextFields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull TextField[] fields) {
        Objects.requireNonNull(fields);
        this.category = category;
        this.recordId = recordId;

        // Check and copy fields
        this.fields = new TextField[fields.length];
        int maxIndex = fields.length - 1;
        for (int index = TextFields.FIRST_FIELD_INDEX; index < fields.length; index++) {
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
    public @NotNull TextField[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public @NotNull List<TextField> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public @NotNull List<TextField> listOfFieldsReversed() {
        var fieldList = listOfFields();
        Collections.reverse(fieldList);
        return fieldList;
    }

    @Override
    public @NotNull Stream<TextField> streamOfFields() {
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
