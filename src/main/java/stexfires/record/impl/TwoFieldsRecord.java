package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record TwoFieldsRecord(@Nullable String category, @Nullable Long recordId,
                              @NotNull Field firstField, @NotNull Field secondField)
        implements TextRecord, Serializable {

    public static final int FIRST_INDEX = Fields.FIRST_FIELD_INDEX;
    public static final int SECOND_INDEX = Fields.FIRST_FIELD_INDEX + 1;
    public static final int MAX_INDEX = SECOND_INDEX;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    public TwoFieldsRecord(@Nullable String firstText, @Nullable String secondText) {
        this(null, null, firstText, secondText);
    }

    public TwoFieldsRecord(@Nullable String category, @Nullable Long recordId,
                           @Nullable String firstText, @Nullable String secondText) {
        this(category, recordId,
                new Field(FIRST_INDEX, MAX_INDEX, firstText),
                new Field(SECOND_INDEX, MAX_INDEX, secondText));
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
    public Field[] arrayOfFields() {
        return new Field[]{firstField, secondField};
    }

    @Override
    public List<Field> listOfFields() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(firstField);
        list.add(secondField);
        return list;
    }

    @Override
    public List<Field> listOfFieldsReversed() {
        List<Field> list = new ArrayList<>(FIELD_SIZE);
        list.add(secondField);
        list.add(firstField);
        return list;
    }

    @Override
    public Stream<Field> streamOfFields() {
        return Stream.of(firstField, secondField);
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
        return FIELD_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isValidIndex(int index) {
        return index == FIRST_INDEX || index == SECOND_INDEX;
    }

    @SuppressWarnings("ReturnOfNull")
    @Override
    public Field fieldAt(int index) {
        return switch (index) {
            case FIRST_INDEX -> firstField;
            case SECOND_INDEX -> secondField;
            default -> null;
        };
    }

    @Override
    public @NotNull Field firstField() {
        return firstField;
    }

    @Override
    public @NotNull Field lastField() {
        return secondField;
    }

    @Override
    public @NotNull Field secondField() {
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
