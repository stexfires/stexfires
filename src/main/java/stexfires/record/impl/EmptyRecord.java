package stexfires.record.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;
import stexfires.record.Fields;
import stexfires.record.TextRecord;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record EmptyRecord()
        implements TextRecord, Serializable {

    public static final int MAX_INDEX = -1;
    public static final int FIELD_SIZE = MAX_INDEX + 1;

    @Override
    public @NotNull Field[] arrayOfFields() {
        return Fields.emptyArray();
    }

    @Override
    public @NotNull List<Field> listOfFields() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull List<Field> listOfFieldsReversed() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Stream<Field> streamOfFields() {
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
    public @Nullable Field fieldAt(int index) {
        return null;
    }

    @Override
    public @Nullable Field firstField() {
        return null;
    }

    @Override
    public @Nullable Field lastField() {
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
