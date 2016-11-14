package stexfires.util.supplier;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SequencePrimitiveLongSupplier implements LongSupplier {

    private final AtomicLong atomicLong;

    public SequencePrimitiveLongSupplier(long initialValue) {
        atomicLong = new AtomicLong(initialValue);
    }

    @Override
    public long getAsLong() {
        return atomicLong.getAndIncrement();
    }
}
