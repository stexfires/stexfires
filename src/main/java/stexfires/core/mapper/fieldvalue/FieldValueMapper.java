package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * A FieldValueMapper maps a {@link Field} to a new field value.
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

    static FieldValueMapper of(Function<Field, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    String mapToValue(Field field);

    default Function<Field, String> asFunction() {
        return this::mapToValue;
    }

    default FieldValueMapper prepend(String value) {
        Objects.requireNonNull(value);
        return (Field field) -> value + mapToValue(field);
    }

    default FieldValueMapper prepend(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return (Field field) -> valueSupplier.get() + mapToValue(field);
    }

    default FieldValueMapper prepend(Supplier<String> valueSupplier, String delimiter) {
        Objects.requireNonNull(valueSupplier);
        Objects.requireNonNull(delimiter);
        return (Field field) -> valueSupplier.get() + delimiter + mapToValue(field);
    }

    default FieldValueMapper append(String value) {
        Objects.requireNonNull(value);
        return (Field field) -> mapToValue(field) + value;
    }

    default FieldValueMapper append(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return (Field field) -> mapToValue(field) + valueSupplier.get();
    }

    default FieldValueMapper append(String delimiter, Supplier<String> valueSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(valueSupplier);
        return (Field field) -> mapToValue(field) + delimiter + valueSupplier.get();
    }

    default FieldValueMapper append(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return (Field field) -> mapToValue(field) + fieldValueMapper.mapToValue(field);
    }

    default FieldValueMapper append(String delimiter, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(fieldValueMapper);
        return (Field field) -> mapToValue(field) + delimiter + fieldValueMapper.mapToValue(field);
    }

}
