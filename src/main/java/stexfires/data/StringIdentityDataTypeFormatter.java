package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class StringIdentityDataTypeFormatter implements DataTypeFormatter<String> {

    private final Supplier<String> nullSourceSupplier;
    private final Supplier<String> emptySourceSupplier;

    public StringIdentityDataTypeFormatter(@Nullable Supplier<String> nullSourceSupplier,
                                           @Nullable Supplier<String> emptySourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable String source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeFormatException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            return source;
        }
    }

}
