package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeParser<T> {

    static <T> DataTypeParser<T> ofFunction(Function<String, T> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    @Nullable T parse(@Nullable String source) throws DataTypeConverterException;

    default @NotNull Optional<T> parseToOptional(@Nullable String source) throws DataTypeConverterException {
        return Optional.ofNullable(parse(source));
    }

    default Function<String, T> asFunction() {
        return this::parse;
    }

    default DataTypeParser<T> compose(UnaryOperator<String> before) {
        Objects.requireNonNull(before);
        return source -> parse(before.apply(source));
    }

    default <V> DataTypeParser<V> andThen(Function<? super T, ? extends V> after) {
        Objects.requireNonNull(after);
        return source -> after.apply(parse(source));
    }

    default T handleNullSource(@Nullable Supplier<T> nullSourceSupplier) throws DataTypeConverterException {
        if (nullSourceSupplier == null) {
            throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is null.");
        } else {
            return nullSourceSupplier.get();
        }
    }

    default T handleEmptySource(@Nullable Supplier<T> emptySourceSupplier) throws DataTypeConverterException {
        if (emptySourceSupplier == null) {
            throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is empty.");
        } else {
            return emptySourceSupplier.get();
        }
    }

}
