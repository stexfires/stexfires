package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFieldValueMapper implements FieldValueMapper {

    protected final String constantValue;

    public ConstantFieldValueMapper(String constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public String mapToValue(Field field) {
        return constantValue;
    }

}
