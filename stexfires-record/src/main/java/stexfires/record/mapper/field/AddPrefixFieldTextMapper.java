package stexfires.record.mapper.field;

import stexfires.record.TextField;

import java.util.*;

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
    public final String mapToText(TextField field) {
        String text = field.text();
        return (text == null) ? prefix : (prefix + text);
    }

}
