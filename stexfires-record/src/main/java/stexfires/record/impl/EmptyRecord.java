package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public record EmptyRecord()
        implements TextRecord, Serializable {

    public static final int MAX_INDEX = -1;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    @Override
    public @NotNull TextField[] arrayOfFields() {
        return TextFields.emptyArray();
    }

    @Override
    public @NotNull List<TextField> listOfFields() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull List<TextField> listOfFieldsReversed() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Stream<TextField> streamOfFields() {
        return Stream.empty();
    }

    @Override
    public @Nullable String category() {
        return null;
    }

    @Override
    public @Nullable Long recordId() {
        return null;
    }

    @Override
    public int size() {
        return FIELD_SIZE;
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isValidIndex(int index) {
        return false;
    }

    @Override
    public @Nullable TextField fieldAt(int index) {
        return null;
    }

    @Override
    public @Nullable TextField firstField() {
        return null;
    }

    @Override
    public @Nullable TextField lastField() {
        return null;
    }

    @Override
    public @Nullable String textAt(int index) {
        return null;
    }

    @Override
    public @Nullable String textAtOrElse(int index, @Nullable String otherText) {
        return otherText;
    }

    @Override
    public @Nullable String firstText() {
        return null;
    }

    @Override
    public @Nullable String lastText() {
        return null;
    }

}