package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;

import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class AddSuffixFieldTextMapper implements FieldTextMapper {

    private final String suffix;

    public AddSuffixFieldTextMapper(String suffix) {
        Objects.requireNonNull(suffix);
        this.suffix = suffix;
    }

    @Override
    public final @NotNull String mapToText(@NotNull TextField field) {
        String text = field.text();
        return (text == null) ? suffix : text + suffix;
    }

}
