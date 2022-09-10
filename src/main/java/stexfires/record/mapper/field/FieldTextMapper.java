package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A FieldTextMapper maps a {@link stexfires.record.TextField} to a new text.
 * <p>
 * It must be {@code thread-safe} and {@code non-interfering}.
 * It should be {@code immutable} and {@code stateless}.
 * <p>
 * This is a {@code functional interface} whose functional method is {@link #mapToText(stexfires.record.TextField)}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see java.util.stream.Stream#map(Function)
 * @since 0.1
 */
@FunctionalInterface
public interface FieldTextMapper {

    static FieldTextMapper of(Function<TextField, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    String mapToText(@NotNull TextField field);

    default Function<TextField, String> asFunction() {
        return this::mapToText;
    }

    default FieldTextMapper prepend(String text) {
        Objects.requireNonNull(text);
        return field -> text + mapToText(field);
    }

    default FieldTextMapper prepend(Supplier<String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        return field -> textSupplier.get() + mapToText(field);
    }

    default FieldTextMapper prepend(Supplier<String> textSupplier, String delimiter) {
        Objects.requireNonNull(textSupplier);
        Objects.requireNonNull(delimiter);
        return field -> textSupplier.get() + delimiter + mapToText(field);
    }

    default FieldTextMapper append(String text) {
        Objects.requireNonNull(text);
        return field -> mapToText(field) + text;
    }

    default FieldTextMapper append(Supplier<String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        return field -> mapToText(field) + textSupplier.get();
    }

    default FieldTextMapper append(String delimiter, Supplier<String> textSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(textSupplier);
        return field -> mapToText(field) + delimiter + textSupplier.get();
    }

    default FieldTextMapper append(FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(fieldTextMapper);
        return field -> mapToText(field) + fieldTextMapper.mapToText(field);
    }

    default FieldTextMapper append(String delimiter, FieldTextMapper fieldTextMapper) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(fieldTextMapper);
        return field -> mapToText(field) + delimiter + fieldTextMapper.mapToText(field);
    }

}
