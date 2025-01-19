package stexfires.record.producer;

import stexfires.record.TextRecord;

import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public final class CollectionProducer<T extends TextRecord> implements RecordProducer<T> {

    private final Collection<T> recordCollection;

    public CollectionProducer(Collection<T> recordCollection) {
        Objects.requireNonNull(recordCollection);
        this.recordCollection = recordCollection;
    }

    @Override
    public Stream<T> produceStream() {
        return recordCollection.stream();
    }

}
