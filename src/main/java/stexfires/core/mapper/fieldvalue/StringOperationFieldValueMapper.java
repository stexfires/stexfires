package stexfires.core.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Field;
import stexfires.util.StringUnaryOperatorType;

import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringOperationFieldValueMapper implements FieldValueMapper {

    private final UnaryOperator<String> stringUnaryOperator;

    public StringOperationFieldValueMapper(StringUnaryOperatorType stringUnaryOperatorType) {
        this(stringUnaryOperatorType.stringUnaryOperator());
    }

    public StringOperationFieldValueMapper(StringUnaryOperatorType stringUnaryOperatorType, Locale locale) {
        this(stringUnaryOperatorType.stringUnaryOperator(locale));
    }

    public StringOperationFieldValueMapper(UnaryOperator<String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        this.stringUnaryOperator = stringUnaryOperator;
    }

    @Override
    public final String mapToValue(@NotNull Field field) {
        return stringUnaryOperator.apply(field.value());
    }

}
