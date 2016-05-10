package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.util.StringUnaryOperator;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringOperationFieldValueMapper implements FieldValueMapper {

    protected final StringUnaryOperator stringUnaryOperator;
    protected final Locale locale;

    public StringOperationFieldValueMapper(StringUnaryOperator stringUnaryOperator) {
        this(stringUnaryOperator, null);
    }

    public StringOperationFieldValueMapper(StringUnaryOperator stringUnaryOperator, Locale locale) {
        Objects.requireNonNull(stringUnaryOperator);
        this.stringUnaryOperator = stringUnaryOperator;
        this.locale = locale;
    }

    @Override
    public String mapToValue(Field field) {
        return StringUnaryOperator.operate(stringUnaryOperator, field.getValue(), locale);
    }

}
