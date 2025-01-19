package stexfires.record.generator;

import stexfires.record.TextRecord;

import java.time.Instant;
import java.util.*;

/**
 * A GeneratorContext contains the context of a {@link stexfires.record.TextRecord} generation.
 * It is created by a {@link stexfires.record.generator.GeneratorProducer} and passed to a {@link stexfires.record.generator.RecordGenerator}.
 *
 * @see stexfires.record.generator.GeneratorProducer
 * @see stexfires.record.generator.RecordGenerator
 * @see stexfires.record.generator.CategoryGenerator
 * @see stexfires.record.generator.RecordIdGenerator
 * @since 0.1
 */
public record GeneratorContext<T extends TextRecord>(
        Instant time,
        long recordIndex,
        boolean first,
        boolean last,
        Optional<T> firstRecord,
        Optional<T> previousRecord
) {

    public GeneratorContext {
        Objects.requireNonNull(time);
        Objects.requireNonNull(firstRecord);
        Objects.requireNonNull(previousRecord);
    }

}
