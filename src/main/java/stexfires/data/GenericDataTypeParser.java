package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
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
        return new GenericDataTypeParser<>(Charset::forName, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Charset> newCharsetDataTypeParser(@Nullable Charset nullOrEmptySource) {
        return newCharsetDataTypeParserWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeParser<Class> newClassDataTypeParserWithSuppliers(@Nullable Supplier<Class> nullSourceSupplier,
                                                                                   @Nullable Supplier<Class> emptySourceSupplier) {
        return new GenericDataTypeParser<>(source -> {
            try {
                return Class.forName(source);
            } catch (ClassNotFoundException e) {
                throw new DataTypeParseException("ClassNotFoundException");
            }
        }, nullSourceSupplier, emptySourceSupplier);
    }

    @SuppressWarnings("rawtypes")
    public static GenericDataTypeParser<Class> newClassDataTypeParser(@Nullable Class nullOrEmptySource) {
        return newClassDataTypeParserWithSuppliers(() -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<byte[]> newByteArrayDataTypeParserWithSuppliers(@NotNull Function<String, byte[]> parseFunction,
                                                                                        @Nullable Supplier<byte[]> nullSourceSupplier,
                                                                                        @Nullable Supplier<byte[]> emptySourceSupplier) {
        return new GenericDataTypeParser<>(parseFunction, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<byte[]> newByteArrayDataTypeParser(@NotNull Function<String, byte[]> parseFunction,
                                                                           byte @Nullable [] nullOrEmptySource) {
        return newByteArrayDataTypeParserWithSuppliers(parseFunction, () -> nullOrEmptySource, () -> nullOrEmptySource);
    }

    public static GenericDataTypeParser<Long> newLongRadixDataType(int radix,
                                                                   @Nullable Supplier<Long> nullSourceSupplier,
                                                                   @Nullable Supplier<Long> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Long.parseLong(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Integer> newIntegerRadixDataType(int radix,
                                                                         @Nullable Supplier<Integer> nullSourceSupplier,
                                                                         @Nullable Supplier<Integer> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Integer.parseInt(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Short> newShortRadixDataType(int radix,
                                                                     @Nullable Supplier<Short> nullSourceSupplier,
                                                                     @Nullable Supplier<Short> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Short.parseShort(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Byte> newByteRadixDataType(int radix,
                                                                   @Nullable Supplier<Byte> nullSourceSupplier,
                                                                   @Nullable Supplier<Byte> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> Byte.parseByte(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<Double> newDoubleDataType(@Nullable Supplier<Double> nullSourceSupplier,
                                                                  @Nullable Supplier<Double> emptySourceSupplier) {
        return new GenericDataTypeParser<>(Double::valueOf, nullSourceSupplier, emptySourceSupplier);
    }

    public static GenericDataTypeParser<BigInteger> newBigIntegerRadixDataType(int radix,
                                                                               @Nullable Supplier<BigInteger> nullSourceSupplier,
                                                                               @Nullable Supplier<BigInteger> emptySourceSupplier) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Invalid range for radix: " + radix);
        }
        return new GenericDataTypeParser<>(source -> new BigInteger(source, radix), nullSourceSupplier, emptySourceSupplier);
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeParseException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                return parseFunction.apply(source);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException e) {
                throw new DataTypeParseException("Cannot parse source: " + e.getClass().getName());
            }
        }
    }

}
