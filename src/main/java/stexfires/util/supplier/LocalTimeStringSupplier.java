package stexfires.util.supplier;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class LocalTimeStringSupplier implements Supplier<String> {

    public LocalTimeStringSupplier() {
    }

    @Override
    public @NotNull String get() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
