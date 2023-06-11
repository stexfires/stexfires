package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public interface ValueRecord extends TextRecord {

    @NotNull ValueRecord withValue(@Nullable String value);

    @NotNull TextField valueField();

    int valueIndex();

    default @Nullable String value() {
        return valueField().text();
    }

    default @NotNull Optional<String> valueAsOptional() {
        return valueField().asOptional();
    }

    default @NotNull Stream<String> valueAsStream() {
        return valueField().stream();
    }

}
