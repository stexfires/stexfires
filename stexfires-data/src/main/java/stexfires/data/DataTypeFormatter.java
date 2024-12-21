package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeFormatter<T> {

    static <T> DataTypeFormatter<T> ofFunction(Function<@Nullable T, @Nullable String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    @Nullable
    String format(@Nullable T source) throws DataTypeConverterException;

    default @Nullable Optional<String> formatToOptional(@Nullable T source) throws DataTypeConverterException {
        return Optional.ofNullable(format(source));
    }

    default Function<@Nullable T, @Nullable String> asFunction() {
        return this::format;
    }

    default <V> DataTypeFormatter<V> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return source -> format(before.apply(source));
    }

    default DataTypeFormatter<T> andThen(UnaryOperator<@Nullable String> after) {
        Objects.requireNonNull(after);
        return source -> after.apply(format(source));
    }

    default @Nullable String handleNullSource(@Nullable Supplier<@Nullable String> nullSourceSupplier) throws DataTypeConverterException {
        if (nullSourceSupplier == null) {
            throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "Source is null.");
        } else {
            return nullSourceSupplier.get();
        }
    }

}
