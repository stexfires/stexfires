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
public record ManyFieldsRecord(@Nullable String category, @Nullable Long recordId,
                               @NotNull Field[] fields)
        implements TextRecord, Serializable {

    public ManyFieldsRecord() {
        this(null, null, Fields.emptyArray());
    }

    public ManyFieldsRecord(@NotNull Collection<String> texts) {
        this(null, null, Fields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull Collection<String> texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    public ManyFieldsRecord(@NotNull Stream<String> texts) {
        this(null, null, Fields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull Stream<String> texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyFieldsRecord(String... texts) {
        this(null, null, Fields.newArray(texts));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, String... texts) {
        this(category, recordId, Fields.newArray(texts));
    }

    public ManyFieldsRecord(@Nullable String category, @Nullable Long recordId, @NotNull Field[] fields) {
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
    public @NotNull Field[] arrayOfFields() {
        synchronized (fields) {
            return Arrays.copyOf(fields, fields.length);
        }
    }

    @Override
    public @NotNull List<Field> listOfFields() {
        return Arrays.asList(arrayOfFields());
    }

    @Override
    public @NotNull List<Field> listOfFieldsReversed() {
        var fieldList = listOfFields();
        Collections.reverse(fieldList);
        return fieldList;
    }

    @Override
    public @NotNull Stream<Field> streamOfFields() {
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
    public @Nullable Field fieldAt(int index) {
        return ((index >= 0) && (index < fields.length)) ? fields[index] : null;
    }

}
