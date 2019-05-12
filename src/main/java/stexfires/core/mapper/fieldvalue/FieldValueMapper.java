package stexfires.core.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.core.Field;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A FieldValueMapper maps a {@link Field} to a new field value.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #mapToValue(Field)}.
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

    @Nullable String mapToValue(@NotNull Field field);

    default Function<Field, String> asFunction() {
        return this::mapToValue;
    }

    default FieldValueMapper prepend(String value) {
        Objects.requireNonNull(value);
        return field -> value + mapToValue(field);
    }

    default FieldValueMapper prepend(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return field -> valueSupplier.get() + mapToValue(field);
    }

    default FieldValueMapper prepend(Supplier<String> valueSupplier, String delimiter) {
        Objects.requireNonNull(valueSupplier);
        Objects.requireNonNull(delimiter);
        return field -> valueSupplier.get() + delimiter + mapToValue(field);
    }

    default FieldValueMapper append(String value) {
        Objects.requireNonNull(value);
        return field -> mapToValue(field) + value;
    }

    default FieldValueMapper append(Supplier<String> valueSupplier) {
        Objects.requireNonNull(valueSupplier);
        return field -> mapToValue(field) + valueSupplier.get();
    }

    default FieldValueMapper append(String delimiter, Supplier<String> valueSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(valueSupplier);
        return field -> mapToValue(field) + delimiter + valueSupplier.get();
    }

    default FieldValueMapper append(FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(fieldValueMapper);
        return field -> mapToValue(field) + fieldValueMapper.mapToValue(field);
    }

    default FieldValueMapper append(String delimiter, FieldValueMapper fieldValueMapper) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(fieldValueMapper);
        return field -> mapToValue(field) + delimiter + fieldValueMapper.mapToValue(field);
    }

}
