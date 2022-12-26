package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeFormatter<T> {

    static <T> DataTypeFormatter<T> ofFunction(Function<T, String> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    @Nullable String format(@Nullable T source) throws DataTypeFormatException;

    default @Nullable Optional<String> formatToOptional(@Nullable T source) throws DataTypeFormatException {
        return Optional.ofNullable(format(source));
    }

    default Function<T, String> asFunction() {
        return this::format;
    }

    default DataTypeFormatter<T> andThen(UnaryOperator<String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        return source -> stringUnaryOperator.apply(format(source));
    }

}
