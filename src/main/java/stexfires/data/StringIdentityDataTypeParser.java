package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class StringIdentityDataTypeParser implements DataTypeParser<String> {

    private final Supplier<String> nullSourceSupplier;
    private final Supplier<String> emptySourceSupplier;

    public StringIdentityDataTypeParser(@Nullable Supplier<String> nullSourceSupplier,
                                        @Nullable Supplier<String> emptySourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable String parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeParseException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else if (source.isEmpty()) {
            if (emptySourceSupplier == null) {
                throw new DataTypeParseException("Source is empty.");
            } else {
                return emptySourceSupplier.get();
            }
        } else {
            return source;
        }
    }

}
