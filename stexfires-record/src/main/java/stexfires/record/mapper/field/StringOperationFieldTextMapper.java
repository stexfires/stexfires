package stexfires.record.mapper.field;

import stexfires.record.TextField;

import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * @since 0.1
 */
public class StringOperationFieldTextMapper implements FieldTextMapper {

    private final UnaryOperator<String> fieldTextFunction;

    public StringOperationFieldTextMapper(UnaryOperator<String> fieldTextFunction) {
        Objects.requireNonNull(fieldTextFunction);
        this.fieldTextFunction = fieldTextFunction;
    }

    @Override
    public final String mapToText(TextField field) {
        return fieldTextFunction.apply(field.text());
    }

}
