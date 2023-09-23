package stexfires.record.generator;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

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
        @NotNull Instant time,
        long recordIndex,
        boolean first,
        boolean last,
        @NotNull Optional<T> firstRecord,
        @NotNull Optional<T> previousRecord
) {

    public GeneratorContext {
        Objects.requireNonNull(time);
        Objects.requireNonNull(firstRecord);
        Objects.requireNonNull(previousRecord);
    }

}
