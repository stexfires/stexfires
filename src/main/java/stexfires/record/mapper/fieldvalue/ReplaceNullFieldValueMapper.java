package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReplaceNullFieldValueMapper implements FieldValueMapper {

    private final String nullValue;

    public ReplaceNullFieldValueMapper(@NotNull String nullValue) {
        Objects.requireNonNull(nullValue);
        this.nullValue = nullValue;
    }

    @Override
    public final @NotNull String mapToValue(@NotNull Field field) {
        return field.valueOrElse(nullValue);
    }

}
