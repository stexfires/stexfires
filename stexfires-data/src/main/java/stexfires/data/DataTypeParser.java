package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeParser<T> {

    static <T> DataTypeParser<T> ofFunction(Function<@Nullable String, @Nullable T> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    @SuppressWarnings("NullableProblems")
    @Nullable
    T parse(@Nullable String source) throws DataTypeConverterException;

    default Optional<T> parseToOptional(@Nullable String source) throws DataTypeConverterException {
        return Optional.ofNullable(parse(source));
    }

    default Function<@Nullable String, @Nullable T> asFunction() {
        return this::parse;
    }

    default DataTypeParser<T> compose(UnaryOperator<@Nullable String> before) {
        Objects.requireNonNull(before);
        return source -> parse(before.apply(source));
    }

    default <V> DataTypeParser<V> andThen(Function<? super T, ? extends V> after) {
        Objects.requireNonNull(after);
        return source -> after.apply(parse(source));
    }

    default @Nullable T handleNullSource(@Nullable Supplier<@Nullable T> nullSourceSupplier) throws DataTypeConverterException {
        if (nullSourceSupplier == null) {
            throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is null.");
        } else {
            return nullSourceSupplier.get();
        }
    }

    default @Nullable T handleEmptySource(@Nullable Supplier<@Nullable T> emptySourceSupplier) throws DataTypeConverterException {
        if (emptySourceSupplier == null) {
            throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is empty.");
        } else {
            return emptySourceSupplier.get();
        }
    }

}
