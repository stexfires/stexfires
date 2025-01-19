package stexfires.record.mapper.field;

import stexfires.record.TextField;

import java.util.*;

/**
 * @since 0.1
 */
public class AddSuffixFieldTextMapper implements FieldTextMapper {

    private final String suffix;

    public AddSuffixFieldTextMapper(String suffix) {
        Objects.requireNonNull(suffix);
        this.suffix = suffix;
    }

    @Override
    public final String mapToText(TextField field) {
        String text = field.text();
        return (text == null) ? suffix : (text + suffix);
    }

}
