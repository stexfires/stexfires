package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddPrefixFieldValueMapper implements FieldValueMapper {

    private final String prefix;

    public AddPrefixFieldValueMapper(String prefix) {
        Objects.requireNonNull(prefix);
        this.prefix = prefix;
    }

    @Override
    public final @NotNull String mapToValue(@NotNull Field field) {
        String text = field.text();
        return (text == null) ? prefix : prefix + text;
    }

}
