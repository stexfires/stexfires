package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.record.SingleRecord;
import org.textfiledatatools.util.Strings;

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

    protected final List<SingleRecord> records;

    public SingleProducer(Collection<?> values) {
        this(null, Records.recordIdSequence(), values);
    }

    public SingleProducer(String category, Collection<?> values) {
        this(category, Records.recordIdSequence(), values);
    }

    public SingleProducer(String category, Supplier<Long> recordIdSupplier, Collection<?> values) {
        this(category, recordIdSupplier, values, Strings::asString);
    }

    public SingleProducer(String category, Supplier<Long> recordIdSupplier, Collection<?> values, Function<Object, String> toStringFunction) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(values);
        records = values
                .stream()
                .map(value -> new SingleRecord(category, recordIdSupplier.get(), toStringFunction.apply(value)))
                .collect(Collectors.toList());
    }

    @Override
    public Stream<SingleRecord> produceStream() {
        return records.stream();
    }

}
