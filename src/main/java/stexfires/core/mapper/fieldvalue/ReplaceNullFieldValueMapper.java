package stexfires.core.mapper.fieldvalue;

import stexfires.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReplaceNullFieldValueMapper implements FieldValueMapper {

    protected final String nullValue;

    public ReplaceNullFieldValueMapper(String nullValue) {
        Objects.requireNonNull(nullValue);
        this.nullValue = nullValue;
    }

    @Override
    public final String mapToValue(Field field) {
        return field.getValueOrElse(nullValue);
    }

}
