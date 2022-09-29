package stexfires.util.function;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SequenceLongSupplier implements Supplier<Long> {

    private final AtomicLong atomicLong;

    public SequenceLongSupplier(long initialValue) {
        atomicLong = new AtomicLong(initialValue);
    }

    @Override
    public @NotNull Long get() {
        return atomicLong.getAndIncrement();
    }

}
