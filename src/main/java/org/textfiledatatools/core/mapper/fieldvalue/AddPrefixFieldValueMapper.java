package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPrefixFieldValueMapper implements FieldValueMapper {

    protected final String prefix;

    public AddPrefixFieldValueMapper(String prefix) {
        Objects.requireNonNull(prefix);
        this.prefix = prefix;
    }

    @Override
    public String mapToValue(Field field) {
        String value = field.getValue();
        if (value == null) {
            value = prefix;
        } else {
            value = prefix + value;
        }
        return value;
    }

}
