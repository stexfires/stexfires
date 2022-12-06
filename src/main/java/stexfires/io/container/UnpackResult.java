package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.Optional;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record UnpackResult<T extends TextRecord>(
        Optional<T> record,
        Optional<String> errorMessage) {
}
