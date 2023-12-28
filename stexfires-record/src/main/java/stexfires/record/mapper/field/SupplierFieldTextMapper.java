package stexfires.record.mapper.field;

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
    public final String mapToText(TextField field) {
        return textSupplier.get();
    }

}
