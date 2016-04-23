package org.textfiledatatools.core.record;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.core.Record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class EmptyRecord implements Record {

    private static final long serialVersionUID = 1L;

    private static final int HASH_CODE = 0;

    public EmptyRecord() {
    }

    @Override
    public Field[] toNewArray() {
        return new Field[0];
    }

    @Override
    public List<Field> toNewList() {
        return new ArrayList<>(0);
    }

    @Override
    public Stream<Field> toNewStream() {
        return Stream.empty();
    }

    @Override
    public String getCategory() {
        return null;
    }

    @Override
    public Long getRecordId() {
        return null;
    }

    @Override
    public int size() {
        return 0;
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
    public Field getFieldAt(int index) {
        return null;
    }

    @Override
    public Field getFirstField() {
        return null;
    }

    @Override
    public Field getLastField() {
        return null;
    }

    @Override
    public String getValueAt(int index) {
        return null;
    }

    @Override
    public String getValueAtOrElse(int index, String other) {
        return other;
    }

    @Override
    public String getValueOfFirstField() {
        return null;
    }

    @Override
    public String getValueOfLastField() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
