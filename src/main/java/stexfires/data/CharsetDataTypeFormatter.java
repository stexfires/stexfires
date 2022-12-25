package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CharsetDataTypeFormatter implements DataTypeFormatter<Charset> {

    private final Supplier<String> nullSourceSupplier;

    public CharsetDataTypeFormatter(@Nullable Supplier<String> nullSourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public @Nullable String format(@Nullable Charset source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            return source.name();
        }
    }

}
