package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class LocaleDataTypeFormatter implements DataTypeFormatter<Locale> {

    private final Supplier<String> nullSourceSupplier;

    public LocaleDataTypeFormatter(@Nullable Supplier<String> nullSourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
    }

    @Override
    public String format(Locale source) throws DataTypeFormatException {
        if (source == null) {
            if (nullSourceSupplier == null) {
                throw new DataTypeFormatException("Source is null.");
            } else {
                return nullSourceSupplier.get();
            }
        } else {
            return source.toLanguageTag();
        }
    }

}
