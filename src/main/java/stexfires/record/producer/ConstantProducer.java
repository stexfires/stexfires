package stexfires.record.producer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueCommentRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.ManyValuesRecord;
import stexfires.record.impl.OneValueRecord;
import stexfires.record.impl.TwoValuesRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantProducer<T extends TextRecord> implements RecordProducer<T> {

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
        return new ConstantProducer<>(streamSize, TextRecords.empty());
    }

    public static ConstantProducer<OneValueRecord> oneValueRecords(long streamSize,
                                                                   @Nullable String value) {
        return new ConstantProducer<>(streamSize, new OneValueRecord(value));
    }

    public static ConstantProducer<TwoValuesRecord> twoValuesRecords(long streamSize,
                                                                     @Nullable String firstValue, @Nullable String secondValue) {
        return new ConstantProducer<>(streamSize, new TwoValuesRecord(firstValue, secondValue));
    }

    public static ConstantProducer<KeyValueRecord> keyValueRecords(long streamSize,
                                                                   @NotNull String key, @Nullable String value) {
        return new ConstantProducer<>(streamSize, new KeyValueRecord(key, value));
    }

    public static ConstantProducer<KeyValueCommentRecord> keyValueCommentRecords(long streamSize,
                                                                                 @NotNull String key, @Nullable String value, @Nullable String comment) {
        return new ConstantProducer<>(streamSize, new KeyValueCommentRecord(key, value, comment));
    }

    public static ConstantProducer<ManyValuesRecord> manyValuesRecords(long streamSize,
                                                                       @NotNull Collection<String> values) {
        return new ConstantProducer<>(streamSize, new ManyValuesRecord(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static ConstantProducer<ManyValuesRecord> manyValuesRecords(long streamSize,
                                                                       String... values) {
        return new ConstantProducer<>(streamSize, new ManyValuesRecord(values));
    }

    @Override
    public final Stream<T> produceStream() {
        return Stream.generate(() -> constantRecord).limit(streamSize);
    }

}
