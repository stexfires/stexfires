package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.util.*;
import java.util.function.*;

/**
 * @since 0.1
 */
public class StringOperationFieldTextMapper implements FieldTextMapper {

    private final UnaryOperator<@Nullable String> fieldTextFunction;

    public StringOperationFieldTextMapper(UnaryOperator<@Nullable String> fieldTextFunction) {
        Objects.requireNonNull(fieldTextFunction);
        this.fieldTextFunction = fieldTextFunction;
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return fieldTextFunction.apply(field.text());
    }

}
