package stexfires.data;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class LocaleDataTypeParser implements DataTypeParser<Locale> {

    private final Supplier<Locale> nullSourceSupplier;
    private final Supplier<Locale> emptySourceSupplier;

    public LocaleDataTypeParser(@Nullable Supplier<Locale> nullSourceSupplier,
                                @Nullable Supplier<Locale> emptySourceSupplier) {
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    @Override
    public @Nullable Locale parse(@Nullable String source) throws DataTypeParseException {
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
            return Locale.forLanguageTag(source);
        }
    }

}
