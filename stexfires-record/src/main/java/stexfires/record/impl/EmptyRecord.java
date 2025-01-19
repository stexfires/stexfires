package stexfires.record.impl;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;
import stexfires.record.TextFields;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public record EmptyRecord()
        implements TextRecord, Serializable {

    public static final int MAX_INDEX = -1;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    @Override
    public TextField[] arrayOfFields() {
        return TextFields.emptyArray();
    }

    @Override
    public List<TextField> listOfFields() {
        return Collections.emptyList();
    }

    @Override
    public List<TextField> listOfFieldsReversed() {
        return Collections.emptyList();
    }

    @Override
    public Stream<TextField> streamOfFields() {
        return Stream.empty();
    }

    @Override
    public @Nullable String category() {
        return null;
    }

    @Override
    public boolean hasCategory() {
        return false;
    }

    @Override
    public @Nullable Long recordId() {
        return null;
    }

    @Override
    public boolean hasRecordId() {
        return false;
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
    public String textAtOrElse(int index, String otherText) {
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
