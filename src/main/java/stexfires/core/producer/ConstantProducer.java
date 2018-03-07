package stexfires.core.producer;

import stexfires.core.Record;
import stexfires.core.Records;
import stexfires.core.record.EmptyRecord;
import stexfires.core.record.KeyValueRecord;
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
public class ConstantProducer<T extends Record> implements RecordProducer<T> {

    private final long streamSize;
    private final T constantRecord;

    public ConstantProducer(long streamSize, T constantRecord) {
        if (streamSize < 0L) {
            throw new IllegalArgumentException("Illegal streamSize! streamSize=" + streamSize);
        }
        Objects.requireNonNull(constantRecord);
        this.streamSize = streamSize;
        this.constantRecord = constantRecord;
    }

    public static ConstantProducer<EmptyRecord> emptyRecords(long streamSize) {
        return new ConstantProducer<>(streamSize, Records.empty());
    }

    public static ConstantProducer<SingleRecord> singleRecords(long streamSize, String value) {
        return new ConstantProducer<>(streamSize, new SingleRecord(value));
    }

    public static ConstantProducer<PairRecord> pairRecords(long streamSize, String firstValue, String secondValue) {
        return new ConstantProducer<>(streamSize, new PairRecord(firstValue, secondValue));
    }

    public static ConstantProducer<KeyValueRecord> keyValueRecords(long streamSize, String key, String value) {
        return new ConstantProducer<>(streamSize, new KeyValueRecord(key, value));
    }

    public static ConstantProducer<StandardRecord> standardRecords(long streamSize, Collection<String> values) {
        return new ConstantProducer<>(streamSize, new StandardRecord(values));
    }

    public static ConstantProducer<StandardRecord> standardRecords(long streamSize, String... values) {
        return new ConstantProducer<>(streamSize, new StandardRecord(values));
    }

    @Override
    public final Stream<T> produceStream() {
        return Stream.generate(() -> constantRecord).limit(streamSize);
    }

}
