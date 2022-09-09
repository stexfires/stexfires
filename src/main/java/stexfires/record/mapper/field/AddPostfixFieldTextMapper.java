package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPostfixFieldTextMapper implements FieldTextMapper {

    private final String postfix;

    public AddPostfixFieldTextMapper(String postfix) {
        Objects.requireNonNull(postfix);
        this.postfix = postfix;
    }

    @Override
    public final @NotNull String mapToText(@NotNull Field field) {
        String text = field.text();
        return (text == null) ? postfix : text + postfix;
    }

}
