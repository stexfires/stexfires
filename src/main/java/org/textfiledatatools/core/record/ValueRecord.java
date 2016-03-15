package org.textfiledatatools.core.record;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public interface ValueRecord extends Record {

    ValueRecord newValueRecord(String value);

    Field getValueField();

    default String getValue() {
        return getValueField().getValue();
    }

    default String getValueOrElse(String other) {
        return !isNullValue() ? getValue() : other;
    }

    default boolean isNullValue() {
        return getValue() == null;
    }

    default boolean isEmptyValue() {
        return !isNullValue() && getValue().isEmpty();
    }

    default boolean isNullOrEmptyValue() {
        return isNullValue() || isEmptyValue();
    }

}
