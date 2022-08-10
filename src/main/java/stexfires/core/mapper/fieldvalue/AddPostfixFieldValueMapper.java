package stexfires.core.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPostfixFieldValueMapper implements FieldValueMapper {

    private final String postfix;

    public AddPostfixFieldValueMapper(String postfix) {
        Objects.requireNonNull(postfix);
        this.postfix = postfix;
    }

    @Override
    public final @NotNull String mapToValue(@NotNull Field field) {
        String value = field.value();
        return (value == null) ? postfix : value + postfix;
    }

}
