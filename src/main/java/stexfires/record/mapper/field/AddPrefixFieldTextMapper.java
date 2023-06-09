package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;

import java.util.Objects;

/**
 * @since 0.1
 */
public class AddPrefixFieldTextMapper implements FieldTextMapper {

    private final String prefix;

    public AddPrefixFieldTextMapper(String prefix) {
        Objects.requireNonNull(prefix);
        this.prefix = prefix;
    }

    @Override
    public final @NotNull String mapToText(@NotNull TextField field) {
        String text = field.text();
        return (text == null) ? prefix : prefix + text;
    }

}
