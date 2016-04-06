package org.textfiledatatools.core.record;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A record contains fields with values.
 * It must be immutable and thread-safe.
 * The field index must start with 0.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface Record extends Serializable {

    Field[] toNewArray();

    List<Field> toNewList();

    Stream<Field> toNewStream();

    String getCategory();

    default String getCategoryOrElse(String other) {
        return getCategory() != null ? getCategory() : other;
    }

    default Optional<String> getCategoryAsOptional() {
        return Optional.ofNullable(getCategory());
    }

    Long getRecordId();

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean isValidIndex(int index) {
        return (index >= 0) && (index < size());
    }

    Field getFieldAt(int index);

    default Field getFirstField() {
        return getFieldAt(0);
    }

    default Field getLastField() {
        return getFieldAt(size() - 1);
    }

    default String getValueAt(int index) {
        return isValidIndex(index) ? getFieldAt(index).getValue() : null;
    }

    default String getValueAtOrElse(int index, String other) {
        return getValueAt(index) != null ? getValueAt(index) : other;
    }

    default String getValueOfFirstField() {
        return !isEmpty() ? getFirstField().getValue() : null;
    }

    default String getValueOfLastField() {
        return !isEmpty() ? getLastField().getValue() : null;
    }

    boolean equals(Object other);

    int hashCode();

    String toString();

}
