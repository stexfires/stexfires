package stexfires.record.producer;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextRecords;
import stexfires.record.ValueRecord;
import stexfires.record.impl.ValueFieldRecord;
import stexfires.util.Strings;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class ValueRecordProducer implements RecordProducer<ValueRecord> {

    private final List<ValueRecord> records;

    public <V> ValueRecordProducer(Collection<V> values) {
        this(null, TextRecords.recordIdSequence(), values, Strings::toNullableString);
    }

    public <V> ValueRecordProducer(@Nullable String category,
                                   Collection<@Nullable V> values) {
        this(category, TextRecords.recordIdSequence(), values, Strings::toNullableString);
    }

    public <V> ValueRecordProducer(@Nullable String category,
                                   Supplier<@Nullable Long> recordIdSupplier,
                                   Collection<@Nullable V> values) {
        this(category, recordIdSupplier, values, Strings::toNullableString);
    }

    public <V> ValueRecordProducer(@Nullable String category,
                                   Supplier<@Nullable Long> recordIdSupplier,
                                   Collection<@Nullable V> values,
                                   Function<? super V, @Nullable String> valueToStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(values);
        Objects.requireNonNull(valueToStringFunction);
        records = values
                .stream()
                .map(value ->
                        new ValueFieldRecord(category,
                                recordIdSupplier.get(),
                                valueToStringFunction.apply(value)))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<ValueRecord> produceStream() {
        return records.stream();
    }

}
