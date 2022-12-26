package stexfires.data;

import org.jetbrains.annotations.NotNull;
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
public interface DataTypeParser<T> {

    static <T> DataTypeParser<T> ofFunction(Function<String, T> function) {
        Objects.requireNonNull(function);
        return function::apply;
    }

    @Nullable T parse(@Nullable String source) throws DataTypeParseException;

    default @NotNull Optional<T> parseToOptional(@Nullable String source) throws DataTypeParseException {
        return Optional.ofNullable(parse(source));
    }

    default Function<String, T> asFunction() {
        return this::parse;
    }

    default DataTypeParser<T> andThen(UnaryOperator<T> dataTypeUnaryOperator) {
        Objects.requireNonNull(dataTypeUnaryOperator);
        return source -> dataTypeUnaryOperator.apply(parse(source));
    }

}
