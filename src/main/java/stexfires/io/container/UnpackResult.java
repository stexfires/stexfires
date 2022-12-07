package stexfires.io.container;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record UnpackResult<T extends TextRecord>(
        @NotNull Optional<T> record,
        @NotNull Optional<String> errorMessage) {

    public UnpackResult {
        Objects.requireNonNull(record);
        Objects.requireNonNull(errorMessage);
    }

}
