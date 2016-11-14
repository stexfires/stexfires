package stexfires.util.supplier;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class LocalTimeStringSupplier implements Supplier<String> {

    @Override
    public String get() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
