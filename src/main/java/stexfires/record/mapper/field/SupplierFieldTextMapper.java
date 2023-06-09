package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.TextField;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public class SupplierFieldTextMapper implements FieldTextMapper {

    private final Supplier<String> textSupplier;

    /**
     * @param textSupplier must be thread-safe
     */
    public SupplierFieldTextMapper(Supplier<String> textSupplier) {
        Objects.requireNonNull(textSupplier);
        this.textSupplier = textSupplier;
    }

    @Override
    public final String mapToText(@NotNull TextField field) {
        return textSupplier.get();
    }

}
