package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

/**
 * @since 0.1
 */
public class IdentityFieldTextMapper implements FieldTextMapper {

    public IdentityFieldTextMapper() {
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return field.text();
    }

}
