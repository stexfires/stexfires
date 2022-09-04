package stexfires.record;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ValueRecord extends TextRecord {

    ValueRecord withValue(@Nullable String value);

    @NotNull Field valueField();

    default @Nullable String value() {
        return valueField().text();
    }

    default Optional<String> valueAsOptional() {
        return Optional.ofNullable(valueField().text());
    }

}
