package stexfires.util.supplier;

import java.util.concurrent.atomic.*;
import java.util.function.*;

/**
 * This class consists of {@code static} utility methods
 * for creating different {@code Supplier}s, which return a sequence of values.
 *
 * @see java.util.function.Supplier
 * @see java.util.function.LongSupplier
 * @since 0.1
 */
public final class SequenceSupplier {

    private final AtomicLong atomicLong;

    private SequenceSupplier(long initialValue) {
        atomicLong = new AtomicLong(initialValue);
    }

    public static Supplier<String> asString(long initialValue) {
        return new SequenceSupplier(initialValue).asStringSupplier();
    }

    public static Supplier<Long> asLong(long initialValue) {
        return new SequenceSupplier(initialValue).asLongSupplier();
    }

    public static LongSupplier asPrimitiveLong(long initialValue) {
        return new SequenceSupplier(initialValue).asPrimitiveLongSupplier();
    }

    private Supplier<String> asStringSupplier() {
        return () -> String.valueOf(atomicLong.getAndIncrement());
    }

    private Supplier<Long> asLongSupplier() {
        return atomicLong::getAndIncrement;
    }

    private LongSupplier asPrimitiveLongSupplier() {
        return atomicLong::getAndIncrement;
    }

}
