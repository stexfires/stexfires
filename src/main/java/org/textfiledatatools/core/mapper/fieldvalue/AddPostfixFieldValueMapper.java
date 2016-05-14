package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPostfixFieldValueMapper implements FieldValueMapper {

    protected final String postfix;

    public AddPostfixFieldValueMapper(String postfix) {
        Objects.requireNonNull(postfix);
        this.postfix = postfix;
    }

    @Override
    public String mapToValue(Field field) {
        String value = field.getValue();
        if (value == null) {
            value = postfix;
        } else {
            value += postfix;
        }
        return value;
    }

}
