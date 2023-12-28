package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public record UnpackResult(
        Optional<TextRecord> record,
        Optional<String> errorMessage) {

    public UnpackResult {
        Objects.requireNonNull(record);
        Objects.requireNonNull(errorMessage);
    }

    public Stream<TextRecord> recordStream() {
        return record.stream();
    }

    public Stream<String> errorMessageStream() {
        return errorMessage.stream();
    }

}
