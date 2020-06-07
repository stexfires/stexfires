package stexfires.core.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.core.Field;

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
        String value = field.getValue();
        return (value == null) ? prefix : prefix + value;
    }

}
