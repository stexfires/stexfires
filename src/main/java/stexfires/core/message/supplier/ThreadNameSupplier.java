package stexfires.core.message.supplier;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ThreadNameSupplier implements Supplier<String> {

    @Override
    public String get() {
        return Thread.currentThread().getName();
    }

}
