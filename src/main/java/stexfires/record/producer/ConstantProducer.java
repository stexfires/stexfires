package stexfires.record.producer;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextRecord;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @since 0.1
 */
public final class ConstantProducer<T extends TextRecord> implements RecordProducer<T> {

    private final long streamSize;
    private final T constantRecord;

    public ConstantProducer(long streamSize, @NotNull T constantRecord) {
        if (streamSize < 0L) {
            throw new IllegalArgumentException("Illegal streamSize! streamSize=" + streamSize);
        }
        Objects.requireNonNull(constantRecord);
        this.streamSize = streamSize;
        this.constantRecord = constantRecord;
    }

    @Override
    public @NotNull Stream<T> produceStream() {
        return Stream.generate(() -> constantRecord).limit(streamSize);
    }

}
