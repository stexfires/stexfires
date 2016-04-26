package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.producer.RecordProducer;
import org.textfiledatatools.core.record.StandardRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class SplittedProducer implements RecordProducer<Record> {

    private final List<Record> records;

    public SplittedProducer(int recordSize, String... values) {
        this(recordSize, null, null, values);
    }

    public SplittedProducer(int recordSize, String category, Long recordId, String... values) {
        if (recordSize <= 0) {
            throw new IllegalArgumentException("Illegal record size! " + recordSize);
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
            records.add(i, new StandardRecord(category, recordId, recordValues));
        }
    }

    @Override
    public Stream<Record> produceStream() {
        return records.stream();
    }

}
