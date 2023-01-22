package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
@SuppressWarnings("UseOfObsoleteDateTimeApi")
public final class GenericDataTypeFormatter<T> implements DataTypeFormatter<T> {

    private final Function<T, String> formatFunction;
    private final Supplier<String> nullSourceSupplier;

    public GenericDataTypeFormatter(@NotNull Function<T, String> formatFunction,
                                    @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(formatFunction);
        this.formatFunction = formatFunction;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static GenericDataTypeFormatter<Locale> newLocaleDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Locale::toLanguageTag, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Locale> newLocaleDataTypeFormatter(@Nullable String nullSource) {
        return newLocaleDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Charset> newCharsetDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Charset::name, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Charset> newCharsetDataTypeFormatter(@Nullable String nullSource) {
        return newCharsetDataTypeFormatterWithSupplier(() -> nullSource);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeFormatter<Class> newClassDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Class::getName, nullSourceSupplier);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeFormatter<Class> newClassDataTypeFormatter(@Nullable String nullSource) {
        return newClassDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Instant> newInstantEpochSecondDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> String.valueOf(source.getEpochSecond()), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Instant> newInstantEpochSecondDataTypeFormatter(@Nullable String nullSource) {
        return newInstantEpochSecondDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Instant> newInstantEpochMilliDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> String.valueOf(source.toEpochMilli()), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Instant> newInstantEpochMilliDataTypeFormatter(@Nullable String nullSource) {
        return newInstantEpochMilliDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Character> newCharacterDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Object::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Character> newCharacterDataTypeFormatter(@Nullable String nullSource) {
        return newCharacterDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Currency> newCurrencyDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Currency::getCurrencyCode, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Currency> newCurrencyDataTypeFormatter(@Nullable String nullSource) {
        return newCurrencyDataTypeFormatterWithSupplier(() -> nullSource);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static GenericDataTypeFormatter<URI> newUriDataTypeFormatterWithSupplier(boolean parseServerAuthority,
                                                                                    @Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> {
            try {
                if (parseServerAuthority) {
                    source.parseServerAuthority();
                }
                return source.toString();
            } catch (URISyntaxException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "Source is not a valid URI");
            }
        }, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<URI> newUriDataTypeFormatter(boolean parseServerAuthority,
                                                                        @Nullable String nullSource) {
        return newUriDataTypeFormatterWithSupplier(parseServerAuthority, () -> nullSource);
    }

    public static GenericDataTypeFormatter<UUID> newUuidDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(UUID::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<UUID> newUuidDataTypeFormatter(@Nullable String nullSource) {
        return newUuidDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Date> newDateDataTypeFormatterWithSupplier(@NotNull DataTypeFormatter<Instant> instantDataTypeFormatter,
                                                                                      @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(instantDataTypeFormatter);
        return new GenericDataTypeFormatter<>(source -> instantDataTypeFormatter.format(source.toInstant()), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Date> newDateDataTypeFormatter(@NotNull DataTypeFormatter<Instant> instantDataTypeFormatter,
                                                                          @Nullable String nullSource) {
        return newDateDataTypeFormatterWithSupplier(instantDataTypeFormatter, () -> nullSource);
    }

    public static GenericDataTypeFormatter<Path> newPathDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Path::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Path> newPathDataTypeFormatter(@Nullable String nullSource) {
        return newPathDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<InetAddress> newInetAddressHostAddressDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(InetAddress::getHostAddress, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<InetAddress> newInetAddressHostAddressDataTypeFormatter(@Nullable String nullSource) {
        return newInetAddressHostAddressDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<InetAddress> newInetAddressHostNameDataTypeFormatterWithSupplier(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(InetAddress::getHostName, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<InetAddress> newInetAddressHostNameDataTypeFormatter(@Nullable String nullSource) {
        return newInetAddressHostNameDataTypeFormatterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<byte[]> newByteArrayDataTypeFormatterWithSupplier(@NotNull Function<byte[], String> formatFunction,
                                                                                             @Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(formatFunction, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<byte[]> newByteArrayDataTypeFormatter(@NotNull Function<byte[], String> formatFunction,
                                                                                 @Nullable String nullSource) {
        return newByteArrayDataTypeFormatterWithSupplier(formatFunction, () -> nullSource);
    }

    public static GenericDataTypeFormatter<Long> newLongRadixDataType(int radix, @Nullable Supplier<String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Long.toString(source, radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Integer> newIntegerRadixDataType(int radix, @Nullable Supplier<String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source, radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Short> newShortRadixDataType(int radix, @Nullable Supplier<String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source.intValue(), radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Byte> newByteRadixDataType(int radix, @Nullable Supplier<String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source.intValue(), radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Double> newDoubleDataType(@Nullable Supplier<String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> Double.toString(source), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<BigInteger> newBigIntegerRadixDataType(int radix, @Nullable Supplier<String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> source.toString(radix), nullSourceSupplier);
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                return formatFunction.apply(source);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "Cannot format source: " + e.getClass().getName());
            }
        }
    }

}
