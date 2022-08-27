package stexfires.record.mapper.fieldvalue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFieldValueMapper implements FieldValueMapper {

    private final String constantValue;

    public ConstantFieldValueMapper() {
        this(null);
    }

    /**
     * @param constantValue can be {@code null}
     */
    public ConstantFieldValueMapper(@Nullable String constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public final String mapToValue(@NotNull Field field) {
        return constantValue;
    }

}
