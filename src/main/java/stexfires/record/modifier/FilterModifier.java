package stexfires.record.modifier;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;
import stexfires.record.filter.RecordFilter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class FilterModifier<T extends TextRecord> implements RecordStreamModifier<T, T> {

    private final RecordFilter<? super T> recordFilter;

    public FilterModifier(RecordFilter<? super T> recordFilter) {
        Objects.requireNonNull(recordFilter);
        this.recordFilter = recordFilter;
    }

    @Override
    public final @NotNull Stream<T> modify(Stream<T> recordStream) {
        return recordStream.filter(recordFilter::isValid);
    }

}
