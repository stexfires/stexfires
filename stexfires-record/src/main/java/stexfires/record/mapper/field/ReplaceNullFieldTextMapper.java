package stexfires.record.mapper.field;

import stexfires.record.TextField;

import java.util.*;

/**
 * @since 0.1
 */
public class ReplaceNullFieldTextMapper implements FieldTextMapper {

    private final String nullText;

    public ReplaceNullFieldTextMapper(String nullText) {
        Objects.requireNonNull(nullText);
        this.nullText = nullText;
    }

    @Override
    public final String mapToText(TextField field) {
        return field.orElse(nullText);
    }

}
