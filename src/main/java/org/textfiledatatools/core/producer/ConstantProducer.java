package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.record.EmptyRecord;
import org.textfiledatatools.core.record.PairRecord;
import org.textfiledatatools.core.record.SingleRecord;
import org.textfiledatatools.core.record.StandardRecord;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantProducer implements RecordProducer<Record> {

    private final List<Record> records;

    public ConstantProducer(int streamSize) {
        this(streamSize, new EmptyRecord());
    }

    public ConstantProducer(int streamSize, String value) {
        this(streamSize, new SingleRecord(value));
    }

    public ConstantProducer(int streamSize, String category, Long recordId, String value) {
        this(streamSize, new SingleRecord(category, recordId, value));
    }

    public ConstantProducer(int streamSize, String firstValue, String secondValue) {
        this(streamSize, new PairRecord(firstValue, secondValue));
    }

    public ConstantProducer(int streamSize, String category, Long recordId, String firstValue, String secondValue) {
        this(streamSize, new PairRecord(category, recordId, firstValue, secondValue));
    }

    public ConstantProducer(int streamSize, Collection<String> values) {
        this(streamSize, new StandardRecord(values));
    }

    public ConstantProducer(int streamSize, String category, Long recordId, Collection<String> values) {
        this(streamSize, new StandardRecord(category, recordId, values));
    }

    public ConstantProducer(int streamSize, String... values) {
        this(streamSize, new StandardRecord(values));
    }

    public ConstantProducer(int streamSize, String category, Long recordId, String... values) {
        this(streamSize, new StandardRecord(category, recordId, values));
    }

    public ConstantProducer(int streamSize, Record record) {
        Objects.requireNonNull(record);
        // throws an IllegalArgumentException if streamSize < 0
        records = Collections.nCopies(streamSize, record);
    }

    @Override
    public Stream<Record> produceStream() {
        return records.stream();
    }

}
