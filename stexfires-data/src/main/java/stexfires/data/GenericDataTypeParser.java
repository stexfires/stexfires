package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.InvalidPathException;
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

    public static GenericDataTypeParser<Locale> forLocaleWithSuppliers(@Nullable Supplier<Locale> nullSourceSupplier,
                                                                       @Nullable Supplier<Locale> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Locale::forLanguageTag, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Locale> forLocale(@Nullable Locale nullOrEmptySource) {
        return forLocaleWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Charset> forCharsetWithSuppliers(@Nullable Supplier<Charset> nullSourceSupplier,
                                                                         @Nullable Supplier<Charset> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Charset::forName, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Charset> forCharset(@Nullable Charset nullOrEmptySource) {
        return forCharsetWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeParser<Class> forClassWithSuppliers(@Nullable Supplier<Class> nullSourceSupplier,
                                                                     @Nullable Supplier<Class> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            try {
                return Class.forName(source);
            } catch (ClassNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "ClassNotFoundException");
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeParser<Class> forClass(@Nullable Class nullOrEmptySource) {
        return forClassWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Instant> forInstantEpochSecondWithSuppliers(@Nullable Supplier<Instant> nullSourceSupplier,
                                                                                    @Nullable Supplier<Instant> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> Instant.ofEpochSecond(Long.parseLong(source)), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Instant> forInstantEpochSecond(@Nullable Instant nullOrEmptySource) {
        return forInstantEpochSecondWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Instant> forInstantEpochMilliWithSuppliers(@Nullable Supplier<Instant> nullSourceSupplier,
                                                                                   @Nullable Supplier<Instant> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> Instant.ofEpochMilli(Long.parseLong(source)), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Instant> forInstantEpochMilli(@Nullable Instant nullOrEmptySource) {
        return forInstantEpochMilliWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Character> forCharacterWithSuppliers(@Nullable Supplier<Character> nullSourceSupplier,
                                                                             @Nullable Supplier<Character> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            if (source.length() > 1) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Invalid length: " + source.length());
            }
            return source.charAt(0);
        }, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Character> forCharacter(@Nullable Character nullOrEmptySource) {
        return forCharacterWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Currency> forCurrencyWithSuppliers(@Nullable Supplier<Currency> nullSourceSupplier,
                                                                           @Nullable Supplier<Currency> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Currency::getInstance, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Currency> forCurrency(@Nullable Currency nullOrEmptySource) {
        return forCurrencyWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static GenericDataTypeParser<URI> forUriWithSuppliers(boolean parseServerAuthority,
                                                                 @Nullable Supplier<URI> nullSourceSupplier,
                                                                 @Nullable Supplier<URI> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            try {
                URI uri = new URI(source);
                if (parseServerAuthority) {
                    uri.parseServerAuthority();
                }
                return uri;
            } catch (URISyntaxException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is not a valid URI");
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<URI> forUri(boolean parseServerAuthority,
                                                    @Nullable URI nullOrEmptySource) {
        return forUriWithSuppliers(parseServerAuthority, () -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<UUID> forUuidWithSuppliers(@Nullable Supplier<UUID> nullSourceSupplier,
                                                                   @Nullable Supplier<UUID> emptySourceSupplier) {
        return new GenericDataTypeParser<>(UUID::fromString, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<UUID> forUuid(@Nullable UUID nullOrEmptySource) {
        return forUuidWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Path> forPathWithSuppliers(@Nullable Supplier<Path> nullSourceSupplier,
                                                                   @Nullable Supplier<Path> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            try {
                return Path.of(source);
            } catch (InvalidPathException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is not a valid Path");
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Path> forPath(@Nullable Path nullOrEmptySource) {
        return forPathWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<InetAddress> forInetAddressWithSuppliers(@Nullable Supplier<InetAddress> nullSourceSupplier,
                                                                                 @Nullable Supplier<InetAddress> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            try {
                return InetAddress.getByName(source);
            } catch (UnknownHostException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Source is not a valid InetAddress");
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<InetAddress> forInetAddress(@Nullable InetAddress nullOrEmptySource) {
        return forInetAddressWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<byte[]> forByteArrayWithSuppliers(@NotNull Function<String, byte[]> parseFunction,
                                                                          @Nullable Supplier<byte[]> nullSourceSupplier,
                                                                          @Nullable Supplier<byte[]> emptySourceSupplier) {
        return new GenericDataTypeParser<>(parseFunction, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<byte[]> forByteArray(@NotNull Function<String, byte[]> parseFunction,
                                                             byte @Nullable [] nullOrEmptySource) {
        return forByteArrayWithSuppliers(parseFunction, () -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Long> forLong(int radix,
                                                      @Nullable Supplier<Long> nullSourceSupplier,
                                                      @Nullable Supplier<Long> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Long.parseLong(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Integer> forInteger(int radix,
                                                            @Nullable Supplier<Integer> nullSourceSupplier,
                                                            @Nullable Supplier<Integer> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Integer.parseInt(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Short> forShort(int radix,
                                                        @Nullable Supplier<Short> nullSourceSupplier,
                                                        @Nullable Supplier<Short> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Short.parseShort(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Byte> forByte(int radix,
                                                      @Nullable Supplier<Byte> nullSourceSupplier,
                                                      @Nullable Supplier<Byte> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Byte.parseByte(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Double> forDouble(@Nullable Supplier<Double> nullSourceSupplier,
                                                          @Nullable Supplier<Double> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Double::valueOf, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Float> forFloat(@Nullable Supplier<Float> nullSourceSupplier,
                                                        @Nullable Supplier<Float> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Float::valueOf, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<BigInteger> forBigInteger(int radix,
                                                                  @Nullable Supplier<BigInteger> nullSourceSupplier,
                                                                  @Nullable Supplier<BigInteger> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> new BigInteger(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<BigDecimal> forBigDecimal(@Nullable Supplier<BigDecimal> nullSourceSupplier,
                                                                  @Nullable Supplier<BigDecimal> emptySourceSupplier) {
        return new GenericDataTypeParser<>(BigDecimal::new, nullSourceSupplier, emptySourceSupplier);
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                return parseFunction.apply(source);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot parse source: " + e.getClass().getName());
            }
        }
    }

}
