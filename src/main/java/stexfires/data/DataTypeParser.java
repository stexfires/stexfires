package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeParser<T> {

    @Nullable T parse(@Nullable String source) throws DataTypeParseException;

    default @NotNull Optional<T> parseToOptional(@Nullable String source) throws DataTypeParseException {
        return Optional.ofNullable(parse(source));
    }

}
