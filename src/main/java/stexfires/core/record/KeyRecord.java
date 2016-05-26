package stexfires.core.record;

import stexfires.core.Field;
import stexfires.core.Record;

/**
 * The value of a key field must not be null.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyRecord extends Record {

    Field getKeyField();

    default String getValueOfKeyField() {
        return getKeyField().getValue();
    }

}
