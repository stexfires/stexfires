package stexfires.record.producer;

import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecords;
import stexfires.record.impl.ManyValuesRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class DividingProducer implements RecordProducer<ManyValuesRecord> {

    private final List<ManyValuesRecord> records;

    @SuppressWarnings("OverloadedVarargsMethod")
    public DividingProducer(int recordSize, String... values) {
        this(null, TextRecords.recordIdSequence(), recordSize, values);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public DividingProducer(@Nullable String category, int recordSize, String... values) {
        this(category, TextRecords.recordIdSequence(), recordSize, values);
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public DividingProducer(@Nullable String category, Supplier<Long> recordIdSupplier, int recordSize, String... values) {
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

            records.add(new ManyValuesRecord(category, recordIdSupplier.get(), newRecordValues));
        }
    }

    @Override
    public final Stream<ManyValuesRecord> produceStream() {
        return records.stream();
    }

}
