package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;
import stexfires.util.function.StringUnaryOperatorType;

import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class StringOperationFieldTextMapper implements FieldTextMapper {

    private final UnaryOperator<String> stringUnaryOperator;

    public StringOperationFieldTextMapper(StringUnaryOperatorType stringUnaryOperatorType) {
        this(stringUnaryOperatorType.stringUnaryOperator());
    }

    public StringOperationFieldTextMapper(StringUnaryOperatorType stringUnaryOperatorType, Locale locale) {
        this(stringUnaryOperatorType.stringUnaryOperator(locale));
    }

    public StringOperationFieldTextMapper(UnaryOperator<String> stringUnaryOperator) {
        Objects.requireNonNull(stringUnaryOperator);
        this.stringUnaryOperator = stringUnaryOperator;
    }

    @Override
    public final String mapToText(@NotNull TextField field) {
        return stringUnaryOperator.apply(field.text());
    }

}
