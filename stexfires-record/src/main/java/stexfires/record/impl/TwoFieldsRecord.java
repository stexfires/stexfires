package stexfires.record.impl;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public record TwoFieldsRecord(@Nullable String category,
                              @Nullable Long recordId,
                              TextField firstField,
                              TextField secondField)
        implements TextRecord, Serializable {

    public static final int FIRST_INDEX = TextField.FIRST_FIELD_INDEX;
    public static final int SECOND_INDEX = TextField.FIRST_FIELD_INDEX + 1;
    public static final int MAX_INDEX = SECOND_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public TwoFieldsRecord(@Nullable String firstText,
                           @Nullable String secondText) {
        this(null, null, firstText, secondText);
    }

    public TwoFieldsRecord(@Nullable String category,
                           @Nullable Long recordId,
                           @Nullable String firstText,
                           @Nullable String secondText) {
        this(category,
                recordId,
                new TextField(FIRST_INDEX, MAX_INDEX, firstText),
                new TextField(SECOND_INDEX, MAX_INDEX, secondText));
    }

    public TwoFieldsRecord {
        // firstField
        Objects.requireNonNull(firstField);
        if (firstField.index() != FIRST_INDEX) {
            throw new IllegalArgumentException("Wrong 'index' of firstField: " + firstField);
        }
        if (firstField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of firstField: " + firstField);
        }
        // secondField
        Objects.requireNonNull(secondField);
        if (secondField.index() != SECOND_INDEX) {
            throw new IllegalArgumentException("Wrong 'index' of secondField: " + secondField);
        }
        if (secondField.maxIndex() != MAX_INDEX) {
            throw new IllegalArgumentException("Wrong 'maxIndex' of secondField: " + secondField);
        }

    }

    public TwoFieldsRecord withSwappedTexts() {
        return new TwoFieldsRecord(category, recordId, secondField.text(), firstField.text());
    }

    @Override
    public TextField[] arrayOfFields() {
        return new TextField[]{firstField, secondField};
    }

    @Override
    public List<TextField> listOfFields() {
        return List.of(firstField, secondField);
    }

    @Override
    public List<TextField> listOfFieldsReversed() {
        return List.of(secondField, firstField);
    }

    @Override
    public Stream<TextField> streamOfFields() {
        return Stream.of(firstField, secondField);
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
        return FIELD_SIZE;
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isValidIndex(int index) {
        return (index == FIRST_INDEX) || (index == SECOND_INDEX);
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return switch (index) {
            case FIRST_INDEX -> firstField;
            case SECOND_INDEX -> secondField;
            default -> null;
        };
    }

    @Override
    public TextField firstField() {
        return firstField;
    }

    @Override
    public TextField firstFieldOrElseThrow() {
        return firstField;
    }

    @Override
    public TextField lastField() {
        return secondField;
    }

    @Override
    public TextField lastFieldOrElseThrow() {
        return secondField;
    }

    @Override
    public TextField secondField() {
        return secondField;
    }

    @Override
    public @Nullable String firstText() {
        return firstField.text();
    }

    @Override
    public @Nullable String lastText() {
        return secondField.text();
    }

    public @Nullable String secondText() {
        return secondField.text();
    }

}
