package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class GenericDataTypeParser<T> implements DataTypeParser<T> {

    private final Function<String, T> parseFunction;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public GenericDataTypeParser(@NotNull Function<String, T> parseFunction,
                                 @Nullable Supplier<T> nullSourceSupplier,
                                 @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(parseFunction);
        this.parseFunction = parseFunction;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static GenericDataTypeParser<Locale> newLocaleDataTypeParserWithSuppliers(@Nullable Supplier<Locale> nullSourceSupplier,
                                                                                     @Nullable Supplier<Locale> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Locale::forLanguageTag, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Locale> newLocaleDataTypeParser(@Nullable Locale nullOrEmptySource) {
        return newLocaleDataTypeParserWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Charset> newCharsetDataTypeParserWithSuppliers(@Nullable Supplier<Charset> nullSourceSupplier,
                                                                                       @Nullable Supplier<Charset> emptySourceSupplier) {
        return new GenericDataTypeParser<>(
                source -> {
                    try {
                        return Charset.forName(source);
                    } catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
                        throw new DataTypeParseException("Illegal or unsupported charset: " + e.getMessage());
                    }
                },
                nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Charset> newCharsetDataTypeParser(@Nullable Charset nullOrEmptySource) {
        return newCharsetDataTypeParserWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeParseException {
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
            return parseFunction.apply(source);
        }
    }

}
