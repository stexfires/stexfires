package stexfires.record.mapper.field;

import org.jspecify.annotations.Nullable;
import stexfires.record.TextField;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public class SupplierFieldTextMapper implements FieldTextMapper {

    private final Supplier<@Nullable String> textSupplier;

    /**
     * @param textSupplier must be thread-safe
     */
    public SupplierFieldTextMapper(Supplier<@Nullable String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        this.textSupplier = textSupplier;
    }

    @Override
    public final @Nullable String mapToText(TextField field) {
        return textSupplier.get();
    }

}
