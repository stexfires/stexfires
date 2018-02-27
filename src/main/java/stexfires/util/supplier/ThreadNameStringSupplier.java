package stexfires.util.supplier;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ThreadNameStringSupplier implements Supplier<String> {

    public ThreadNameStringSupplier() {
    }

    @Override
    public String get() {
        return Thread.currentThread().getName();
    }

}
