package stexfires.core.record;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Field;
import stexfires.core.TextRecord;

/**
 * The value of a key field must not be null.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyRecord extends TextRecord {

    @NotNull Field getKeyField();

    default @NotNull String getValueOfKeyField() {
        return getKeyField().getValue();
    }

}
