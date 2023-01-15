package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
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
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                return formatFunction.apply(source);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException e) {
                throw new DataTypeFormatException("Cannot format source: " + e.getClass().getName());
            }
        }
    }

}
