package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityFieldValueMapper implements FieldValueMapper {

    public IdentityFieldValueMapper() {
    }

    @Override
    public final String mapToValue(Field field) {
        return field.getValue();
    }

}
