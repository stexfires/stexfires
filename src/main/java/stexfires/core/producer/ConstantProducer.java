package stexfires.core.producer;

import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.record.PairRecord;
import stexfires.core.record.SingleRecord;
import stexfires.core.record.StandardRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantProducer implements RecordProducer<Record> {

    protected final int streamSize;
    protected final Record constantRecord;

    public ConstantProducer(int streamSize) {
        this(streamSize, Records.empty());
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

    public ConstantProducer(int streamSize, Record constantRecord) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("Illegal streamSize! streamSize=" + streamSize);
        }
        Objects.requireNonNull(constantRecord);
        this.streamSize = streamSize;
        this.constantRecord = constantRecord;
    }

    @Override
    public Stream<Record> produceStream() {
        return Stream.generate(() -> constantRecord).limit(streamSize);
    }

}
