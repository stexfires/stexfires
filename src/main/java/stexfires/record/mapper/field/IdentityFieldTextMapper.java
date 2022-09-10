package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityFieldTextMapper implements FieldTextMapper {

    public IdentityFieldTextMapper() {
    }

    @Override
    public final String mapToText(@NotNull TextField field) {
        return field.text();
    }

}
