package org.textfiledatatools.core.mapper.fieldvalue;

import org.textfiledatatools.core.Field;
import org.textfiledatatools.util.StringOperation;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringOperationFieldValueMapper implements FieldValueMapper {

    protected final StringOperation stringOperation;
    protected final Locale locale;

    public StringOperationFieldValueMapper(StringOperation stringOperation) {
        this(stringOperation, null);
    }

    public StringOperationFieldValueMapper(StringOperation stringOperation, Locale locale) {
        Objects.requireNonNull(stringOperation);
        this.stringOperation = stringOperation;
        this.locale = locale;
    }

    @Override
    public String mapToValue(Field field) {
        return StringOperation.operate(stringOperation, field.getValue(), locale);
    }

}
