package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPrefixFieldTextMapper implements FieldTextMapper {

    private final String prefix;

    public AddPrefixFieldTextMapper(String prefix) {
        Objects.requireNonNull(prefix);
        this.prefix = prefix;
    }

    @Override
    public final @NotNull String mapToText(@NotNull Field field) {
        String text = field.text();
        return (text == null) ? prefix : prefix + text;
    }

}
