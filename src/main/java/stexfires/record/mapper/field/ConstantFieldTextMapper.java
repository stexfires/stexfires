package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.record.Field;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public class ConstantFieldTextMapper implements FieldTextMapper {

    private final String constantText;

    public ConstantFieldTextMapper() {
        this(null);
    }

    /**
     * @param constantText can be {@code null}
     */
    public ConstantFieldTextMapper(@Nullable String constantText) {
        this.constantText = constantText;
    }

    @Override
    public final String mapToText(@NotNull Field field) {
        return constantText;
    }

}
