package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

/**
 * @since 0.1
 */
public class ConstantFieldTextMapper implements FieldTextMapper {

    private final @Nullable String constantText;

    /**
     * @param constantText can be {@code null}
     */
    public ConstantFieldTextMapper(@Nullable String constantText) {
        this.constantText = constantText;
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return constantText;
    }

}
