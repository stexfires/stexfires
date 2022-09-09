package stexfires.record.producer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.TextRecord;
import stexfires.record.TextRecords;
import stexfires.record.impl.EmptyRecord;
import stexfires.record.impl.KeyValueCommentRecord;
import stexfires.record.impl.KeyValueRecord;
import stexfires.record.impl.OneFieldRecord;
import stexfires.record.impl.StandardRecord;
import stexfires.record.impl.TwoFieldsRecord;

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

    public static ConstantProducer<OneFieldRecord> oneValueRecords(long streamSize,
                                                                   @Nullable String value) {
        return new ConstantProducer<>(streamSize, new OneFieldRecord(value));
    }

    public static ConstantProducer<TwoFieldsRecord> twoValuesRecords(long streamSize,
                                                                     @Nullable String firstValue, @Nullable String secondValue) {
        return new ConstantProducer<>(streamSize, new TwoFieldsRecord(firstValue, secondValue));
    }

    public static ConstantProducer<KeyValueRecord> keyValueRecords(long streamSize,
                                                                   @NotNull String key, @Nullable String value) {
        return new ConstantProducer<>(streamSize, new KeyValueRecord(key, value));
    }

    public static ConstantProducer<KeyValueCommentRecord> keyValueCommentRecords(long streamSize,
                                                                                 @NotNull String key, @Nullable String value, @Nullable String comment) {
        return new ConstantProducer<>(streamSize, new KeyValueCommentRecord(key, value, comment));
    }

    public static ConstantProducer<StandardRecord> manyValuesRecords(long streamSize,
                                                                     @NotNull Collection<String> values) {
        return new ConstantProducer<>(streamSize, new StandardRecord(values));
    }

    @SuppressWarnings("OverloadedVarargsMethod")
    public static ConstantProducer<StandardRecord> manyValuesRecords(long streamSize,
                                                                     String... values) {
        return new ConstantProducer<>(streamSize, new StandardRecord(values));
    }

    @Override
    public final Stream<T> produceStream() {
        return Stream.generate(() -> constantRecord).limit(streamSize);
    }

}
