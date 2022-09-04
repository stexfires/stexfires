package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

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
        String text = field.text();
        return (text == null) ? postfix : text + postfix;
    }

}
