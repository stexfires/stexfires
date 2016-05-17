package org.textfiledatatools.util;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public enum LineSeparator {

    LF("\n"),
    CR("\r"),
    CR_LF("\r\n");

    private final String separator;

    LineSeparator(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }

    public Supplier<String> supplier() {
        return () -> separator;
    }
    
    @Override
    public String toString() {
        return separator;
    }

}
