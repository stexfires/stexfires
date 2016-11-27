package stexfires.core.producer;

import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.record.StandardRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SplittedProducer implements RecordProducer<Record> {

    protected final List<Record> records;

    public SplittedProducer(int recordSize, String... values) {
        this(null, Records.recordIdSequence(), recordSize, values);
    }

    public SplittedProducer(String category, int recordSize, String... values) {
        this(category, Records.recordIdSequence(), recordSize, values);
    }

    @SuppressWarnings("UnnecessaryBoxing")
    public SplittedProducer(String category, LongSupplier recordIdSupplier, int recordSize, String... values) {
        this(category, () -> Long.valueOf(recordIdSupplier.getAsLong()), recordSize, values);
    }

    public SplittedProducer(String category, Supplier<Long> recordIdSupplier, int recordSize, String... values) {
        Objects.requireNonNull(recordIdSupplier);
        Objects.requireNonNull(values);
        if (recordSize <= 0) {
            throw new IllegalArgumentException("Illegal recordSize! recordSize=" + recordSize);
        }

        int capacity = (values.length + recordSize - 1) / recordSize;
        records = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            List<String> recordValues = new ArrayList<>(recordSize);
            for (int j = 0; j < recordSize; j++) {
                int valueIndex = i * recordSize + j;
                if (valueIndex < values.length) {
                    recordValues.add(values[valueIndex]);
                } else {
                    recordValues.add(null);
                }
            }

            records.add(new StandardRecord(category, recordIdSupplier.get(), recordValues));
        }
    }

    @Override
    public Stream<Record> produceStream() {
        return records.stream();
    }

}
