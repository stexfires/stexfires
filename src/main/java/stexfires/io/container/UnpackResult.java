package stexfires.io.container;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record UnpackResult(
        @NotNull Optional<TextRecord> record,
        @NotNull Optional<String> errorMessage) {

    public UnpackResult {
        Objects.requireNonNull(record);
        Objects.requireNonNull(errorMessage);
    }

    public @NotNull Stream<TextRecord> recordStream() {
        return record.stream();
    }

    public @NotNull Stream<String> errorMessageStream() {
        return errorMessage.stream();
    }

}
