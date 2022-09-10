package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ReplaceNullFieldTextMapper implements FieldTextMapper {

    private final String nullText;

    public ReplaceNullFieldTextMapper(@NotNull String nullText) {
        Objects.requireNonNull(nullText);
        this.nullText = nullText;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public final @NotNull String mapToText(@NotNull Field field) {
        return field.orElse(nullText);
    }

}
