package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.Objects;
import java.util.function.Function;


/**
 * A FieldValueMapper maps a {@link org.textfiledatatools.core.Field} to a new field value.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * <p>This is a functional interface
 * whose functional method is {@link #mapToValue(Field)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface FieldValueMapper {

    static FieldValueMapper addPrefix(String prefix,
                                      FieldValueMapper fieldValueMapper) {
        return addPrefixAndPostfix(prefix, fieldValueMapper, null);
    }

    static FieldValueMapper addPostfix(FieldValueMapper fieldValueMapper,
                                       String postfix) {
        return addPrefixAndPostfix(null, fieldValueMapper, postfix);
    }

    static FieldValueMapper addPrefixAndPostfix(String prefix,
                                                FieldValueMapper fieldValueMapper,
                                                String postfix) {
        Objects.requireNonNull(fieldValueMapper);
        if (prefix != null && postfix != null) {
            return (Field field) -> prefix + fieldValueMapper.mapToValue(field) + postfix;
        } else if (prefix != null) {
            return (Field field) -> prefix + fieldValueMapper.mapToValue(field);
        } else if (postfix != null) {
            return (Field field) -> fieldValueMapper.mapToValue(field) + postfix;
        }
        return fieldValueMapper;
    }

    static FieldValueMapper concat(FieldValueMapper firstFieldValueMapper,
                                   FieldValueMapper secondFieldValueMapper) {
        Objects.requireNonNull(firstFieldValueMapper);
        Objects.requireNonNull(secondFieldValueMapper);
        return (Field field) -> firstFieldValueMapper.mapToValue(field) + secondFieldValueMapper.mapToValue(field);
    }

    static FieldValueMapper concat(FieldValueMapper firstFieldValueMapper,
                                   FieldValueMapper secondFieldValueMapper,
                                   FieldValueMapper thirdFieldValueMapper) {
        Objects.requireNonNull(firstFieldValueMapper);
        Objects.requireNonNull(secondFieldValueMapper);
        Objects.requireNonNull(thirdFieldValueMapper);
        return (Field field) -> firstFieldValueMapper.mapToValue(field)
                + secondFieldValueMapper.mapToValue(field)
                + thirdFieldValueMapper.mapToValue(field);
    }

    String mapToValue(Field field);

}
