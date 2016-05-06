package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.function.Function;


/**
 * A FieldValueMapper maps a {@link org.textfiledatatools.core.Field} to a new field value.
 * <p>
 * It must be immutable and thread-safe.
 * It must be <code>non-interfering</code> and <code>stateless</code>.
 * <p>
 * This is a functional interface whose functional method is {@link #mapToValue(Field)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface FieldValueMapper {

    String mapToValue(Field field);

}
