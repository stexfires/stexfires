package org.textfiledatatools.core.producer;

import org.textfiledatatools.core.Record;
import org.textfiledatatools.core.Records;
import org.textfiledatatools.core.record.PairRecord;
import org.textfiledatatools.core.record.SingleRecord;
import org.textfiledatatools.core.record.StandardRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantProducer implements RecordProducer<Record> {

    private final int streamSize;
    private final Record record;

    public ConstantProducer(int streamSize) {
        this(streamSize, Records.EMPTY_RECORD);
    }

    public ConstantProducer(int streamSize, String value) {
        this(streamSize, new SingleRecord(value));
    }

    public ConstantProducer(String category, int streamSize, String value) {
        this(streamSize, new SingleRecord(category, null, value));
    }

    public ConstantProducer(int streamSize, String firstValue, String secondValue) {
        this(streamSize, new PairRecord(firstValue, secondValue));
    }

    public ConstantProducer(String category, int streamSize, String firstValue, String secondValue) {
        this(streamSize, new PairRecord(category, null, firstValue, secondValue));
    }

    public ConstantProducer(int streamSize, Collection<String> values) {
        this(streamSize, new StandardRecord(values));
    }

    public ConstantProducer(String category, int streamSize, Collection<String> values) {
        this(streamSize, new StandardRecord(category, null, values));
    }

    public ConstantProducer(int streamSize, String... values) {
        this(streamSize, new StandardRecord(values));
    }

    public ConstantProducer(String category, int streamSize, String... values) {
        this(streamSize, new StandardRecord(category, null, values));
    }

    public ConstantProducer(int streamSize, Record record) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("Illegal streamSize! streamSize=" + streamSize);
        }
        Objects.requireNonNull(record);
        this.streamSize = streamSize;
        this.record = record;
    }

    @Override
    public Stream<Record> produceStream() {
        return Stream.generate(() -> record).limit(streamSize);
    }

}
