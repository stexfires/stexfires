package stexfires.io.container;

import stexfires.record.TextRecord;

import java.util.function.Function;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record ContainerField(
        String name,
        int index,
        Function<TextRecord, String> extractFunction) {
}
