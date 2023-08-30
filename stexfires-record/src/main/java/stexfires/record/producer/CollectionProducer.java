package stexfires.record.producer;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class CollectionProducer<T extends TextRecord> implements RecordProducer<T> {

    private final Collection<T> recordCollection;

    public CollectionProducer(@NotNull Collection<T> recordCollection) {
        Objects.requireNonNull(recordCollection);
        this.recordCollection = recordCollection;
    }

    @Override
    public @NotNull Stream<T> produceStream() {
        return recordCollection.stream();
    }

}
