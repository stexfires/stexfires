package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@FunctionalInterface
public interface DataTypeFormatter<T> {

    @Nullable String format(@Nullable T source) throws DataTypeFormatException;

    default @Nullable Optional<String> formatToOptional(@Nullable T source) throws DataTypeFormatException {
        return Optional.ofNullable(format(source));
    }

}
