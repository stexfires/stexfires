package stexfires.record.modifier;

import stexfires.record.TextRecord;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @see stexfires.record.comparator.RecordComparators
 * @since 0.1
 */
public class SortModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

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
