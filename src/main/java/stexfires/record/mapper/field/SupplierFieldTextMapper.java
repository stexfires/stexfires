package stexfires.record.mapper.field;

import org.jetbrains.annotations.NotNull;
import stexfires.record.Field;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
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
    public final String mapToText(@NotNull Field field) {
        return textSupplier.get();
    }

}
