package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.stream.*;

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
