package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class IdentityFieldTextMapper implements FieldTextMapper {

    public IdentityFieldTextMapper() {
    }

    @Override
    public final String mapToText(@NotNull Field field) {
        return field.text();
    }

}
