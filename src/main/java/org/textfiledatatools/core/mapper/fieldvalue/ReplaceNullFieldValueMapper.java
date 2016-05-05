package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReplaceNullFieldValueMapper implements FieldValueMapper {

    private final String nullValue;

    public ReplaceNullFieldValueMapper(String nullValue) {
        Objects.requireNonNull(nullValue);
        this.nullValue = nullValue;
    }

    @Override
    public String mapToValue(Field field) {
        return field.getValueOrElse(nullValue);
    }

}
