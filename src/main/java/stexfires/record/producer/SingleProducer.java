package stexfires.record.producer;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecords;
import stexfires.record.impl.SingleRecord;
import stexfires.util.Strings;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SingleProducer implements RecordProducer<SingleRecord> {

    private final List<SingleRecord> records;

    public <V> SingleProducer(Collection<V> values) {
        this(null, TextRecords.recordIdSequence(), values, Strings::asString);
    }

    public <V> SingleProducer(@Nullable String category, Collection<V> values) {
        this(category, TextRecords.recordIdSequence(), values, Strings::asString);
    }

    public <V> SingleProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Collection<V> values) {
        this(category, recordIdSupplier, values, Strings::asString);
    }

    public <V> SingleProducer(@Nullable String category, Supplier<Long> recordIdSupplier, Collection<V> values,
                              Function<? super V, String> valueToStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(values);
        Objects.requireNonNull(valueToStringFunction);
        records = values
                .stream()
                .map(value -> new SingleRecord(category, recordIdSupplier.get(),
                        valueToStringFunction.apply(value)))
                .collect(Collectors.toList());
    }

    @Override
    public final Stream<SingleRecord> produceStream() {
        return records.stream();
    }

}
