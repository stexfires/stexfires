package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class NullFieldValueMapper implements FieldValueMapper {

    @Override
    public String mapToValue(Field field) {
        return null;
    }

}
