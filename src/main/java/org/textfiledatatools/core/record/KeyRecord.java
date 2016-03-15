package org.textfiledatatools.core.record;

/**
 * The value of a key field must not be null.
 *
 * @author Mathias Kalb
 * @since 0.1
 */
public interface KeyRecord extends Record {

    Field getKeyField();

    default String getKey() {
        return getKeyField().getValue();
    }

    default boolean isEmptyKey() {
        return getKey().isEmpty();
    }

}
