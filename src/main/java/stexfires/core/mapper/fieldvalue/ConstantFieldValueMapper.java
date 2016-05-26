package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFieldValueMapper implements FieldValueMapper {

    protected final String constantValue;

    public ConstantFieldValueMapper() {
        this(null);
    }

    /**
     * @param constantValue can be <code>null</code>
     */
    public ConstantFieldValueMapper(String constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public String mapToValue(Field field) {
        return constantValue;
    }

}
