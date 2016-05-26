package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

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
