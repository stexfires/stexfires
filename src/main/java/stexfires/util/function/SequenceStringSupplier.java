package stexfires.util.function;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class SequenceStringSupplier implements Supplier<String> {

    private final AtomicLong atomicLong;

    public SequenceStringSupplier(long initialValue) {
        atomicLong = new AtomicLong(initialValue);
    }

    @Override
    public @NotNull String get() {
        return String.valueOf(atomicLong.getAndIncrement());
    }

}
