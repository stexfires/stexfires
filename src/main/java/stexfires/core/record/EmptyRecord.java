package stexfires.core.record;

import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;
import stexfires.core.Fields;
import stexfires.core.TextRecord;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class EmptyRecord implements TextRecord {

    public static final int FIELD_SIZE = 0;

    private static final int HASH_CODE = 0;

    public EmptyRecord() {
    }

    @Override
    public Field[] arrayOfFields() {
        return Fields.emptyArray();
    }

    @Override
    public List<Field> listOfFields() {
        return Collections.emptyList();
    }

    @Override
    public Stream<Field> streamOfFields() {
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
    public @Nullable String valueAt(int index) {
        return null;
    }

    @Override
    public String valueAtOrElse(int index, @Nullable String other) {
        return other;
    }

    @Override
    public @Nullable String valueOfFirstField() {
        return null;
    }

    @Override
    public @Nullable String valueOfLastField() {
        return null;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        // always true
        return true;
    }

    @Override
    public int hashCode() {
        return HASH_CODE;
    }

    @Override
    public String toString() {
        return "EmptyRecord{}";
    }

}
