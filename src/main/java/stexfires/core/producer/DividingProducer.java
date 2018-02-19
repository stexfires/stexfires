package stexfires.core.producer;

import stexfires.core.Records;
import stexfires.core.record.StandardRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DividingProducer implements RecordProducer<StandardRecord> {

    protected final List<StandardRecord> records;

    public DividingProducer(int recordSize, String... values) {
        this(null, Records.recordIdSequence(), recordSize, values);
    }

    public DividingProducer(String category, int recordSize, String... values) {
        this(category, Records.recordIdSequence(), recordSize, values);
    }

    public DividingProducer(String category, Supplier<Long> recordIdSupplier, int recordSize, String... values) {
        Objects.requireNonNull(recordIdSupplier);
        if (recordSize <= 0) {
            throw new IllegalArgumentException("Illegal recordSize! recordSize=" + recordSize);
        }
        Objects.requireNonNull(values);
        int capacity = (values.length + recordSize - 1) / recordSize;
        records = new ArrayList<>(capacity);

        for (int recordIndex = 0; recordIndex < capacity; recordIndex++) {
            List<String> newRecordValues = new ArrayList<>(recordSize);
            for (int newValueIndex = 0; newValueIndex < recordSize; newValueIndex++) {
                int originalValueIndex = recordIndex * recordSize + newValueIndex;
                if (originalValueIndex < values.length) {
                    newRecordValues.add(values[originalValueIndex]);
                } else {
                    newRecordValues.add(null);
                }
            }

            records.add(new StandardRecord(category, recordIdSupplier.get(), newRecordValues));
        }
    }

    @Override
    public Stream<StandardRecord> produceStream() {
        return records.stream();
    }

}
