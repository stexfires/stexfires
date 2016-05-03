package org.textfiledatatools.core.message.supplier;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class LocalTimeSupplier implements Supplier<String> {

    @Override
    public String get() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
