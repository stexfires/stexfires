package stexfires.core.modifier;

import stexfires.core.Record;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @see stexfires.core.comparator.RecordComparators
 * @since 0.1
 */
public class SortModifier<T extends Record> implements RecordStreamModifier<T, T> {

    private final Comparator<? super T> recordComparator;

    public SortModifier(Comparator<? super T> recordComparator) {
        Objects.requireNonNull(recordComparator);
        this.recordComparator = recordComparator;
    }

    @Override
    public final Stream<T> modify(Stream<T> recordStream) {
        return recordStream.sorted(recordComparator);
    }

}
