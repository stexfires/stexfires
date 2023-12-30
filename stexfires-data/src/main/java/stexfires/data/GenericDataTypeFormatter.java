package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.io.UncheckedIOException;
import java.math.BigDecimal;
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
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class GenericDataTypeFormatter<T> implements DataTypeFormatter<T> {

    private final Function<T, @Nullable String> formatFunction;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public GenericDataTypeFormatter(Function<T, @Nullable String> formatFunction,
                                    @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(formatFunction);
        this.formatFunction = formatFunction;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static GenericDataTypeFormatter<Locale> forLocaleWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Locale::toLanguageTag, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Locale> forLocale(@Nullable String nullSource) {
        return forLocaleWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Charset> forCharsetWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Charset::name, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Charset> forCharset(@Nullable String nullSource) {
        return forCharsetWithSupplier(() -> nullSource);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeFormatter<Class> forClassWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Class::getName, nullSourceSupplier);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeFormatter<Class> forClass(@Nullable String nullSource) {
        return forClassWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Instant> forInstantEpochSecondWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> String.valueOf(source.getEpochSecond()), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Instant> forInstantEpochSecond(@Nullable String nullSource) {
        return forInstantEpochSecondWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Instant> forInstantEpochMilliWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> String.valueOf(source.toEpochMilli()), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Instant> forInstantEpochMilli(@Nullable String nullSource) {
        return forInstantEpochMilliWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Character> forCharacterWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Object::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Character> forCharacter(@Nullable String nullSource) {
        return forCharacterWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Currency> forCurrencyWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Currency::getCurrencyCode, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Currency> forCurrency(@Nullable String nullSource) {
        return forCurrencyWithSupplier(() -> nullSource);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static GenericDataTypeFormatter<URI> forUriWithSupplier(boolean parseServerAuthority,
                                                                   @Nullable Supplier<@Nullable String> nullSourceSupplier) {
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

    public static GenericDataTypeFormatter<URI> forUri(boolean parseServerAuthority,
                                                       @Nullable String nullSource) {
        return forUriWithSupplier(parseServerAuthority, () -> nullSource);
    }

    public static GenericDataTypeFormatter<UUID> forUuidWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(UUID::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<UUID> forUuid(@Nullable String nullSource) {
        return forUuidWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<Path> forPathWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(Path::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Path> forPath(@Nullable String nullSource) {
        return forPathWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<InetAddress> forInetAddressHostAddressWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(InetAddress::getHostAddress, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<InetAddress> forInetAddressHostAddress(@Nullable String nullSource) {
        return forInetAddressHostAddressWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<InetAddress> forInetAddressHostNameWithSupplier(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(InetAddress::getHostName, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<InetAddress> forInetAddressHostName(@Nullable String nullSource) {
        return forInetAddressHostNameWithSupplier(() -> nullSource);
    }

    public static GenericDataTypeFormatter<byte[]> forByteArrayWithSupplier(Function<byte[], @Nullable String> formatFunction,
                                                                            @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(formatFunction, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<byte[]> forByteArray(Function<byte[], @Nullable String> formatFunction,
                                                                @Nullable String nullSource) {
        return forByteArrayWithSupplier(formatFunction, () -> nullSource);
    }

    public static GenericDataTypeFormatter<Long> forLong(int radix, @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Long.toString(source, radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Integer> forInteger(int radix, @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source, radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Short> forShort(int radix, @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source.intValue(), radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Byte> forByte(int radix, @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> Integer.toString(source.intValue(), radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Double> forDouble(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> Double.toString(source), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<Float> forFloat(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(source -> Float.toString(source), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<BigInteger> forBigInteger(int radix, @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeFormatter<>(source -> source.toString(radix), nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<BigDecimal> forBigDecimal(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(BigDecimal::toString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<BigDecimal> forBigDecimalPlain(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(BigDecimal::toPlainString, nullSourceSupplier);
    }

    public static GenericDataTypeFormatter<BigDecimal> forBigDecimalEngineering(@Nullable Supplier<@Nullable String> nullSourceSupplier) {
        return new GenericDataTypeFormatter<>(BigDecimal::toEngineeringString, nullSourceSupplier);
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
