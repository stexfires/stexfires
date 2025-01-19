package stexfires.record;

import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.stream.*;

/**
 * @since 0.1
 */
public interface ValueRecord extends TextRecord {

    ValueRecord withValue(@Nullable String value);

    TextField valueField();

    int valueIndex();

    default @Nullable String value() {
        return valueField().text();
    }

    default Optional<String> valueAsOptional() {
        return valueField().asOptional();
    }

    default Stream<String> valueAsStream() {
        return valueField().stream();
    }

}
