package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ListDataTypeFormatter<T> implements DataTypeFormatter<List<T>> {

    private final String prefix;
    private final String suffix;
    private final String delimiter;
    private final Predicate<String> stringValueValidator;
    private final DataTypeFormatter<T> dataTypeFormatter;
    private final Supplier<String> nullSourceSupplier;

    public ListDataTypeFormatter(
            @Nullable String prefix,
            @Nullable String suffix,
            @Nullable String delimiter,
            @Nullable Predicate<String> stringValueValidator,
            @NotNull DataTypeFormatter<T> dataTypeFormatter,
            @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        this.prefix = prefix;
        this.suffix = suffix;
        this.stringValueValidator = stringValueValidator;
        this.delimiter = delimiter;
        this.dataTypeFormatter = dataTypeFormatter;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static <T> ListDataTypeFormatter<T> withDelimiter(@Nullable String prefix,
                                                             @Nullable String suffix,
                                                             @NotNull String delimiter,
                                                             @NotNull DataTypeFormatter<T> dataTypeFormatter,
                                                             @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(dataTypeFormatter);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter is empty");
        }
        return new ListDataTypeFormatter<>(prefix, suffix,
                delimiter, stringValue -> !stringValue.contains(delimiter), dataTypeFormatter,
                nullSourceSupplier);
    }

    public static <T> ListDataTypeFormatter<T> withValueLength(@Nullable String prefix,
                                                               @Nullable String suffix,
                                                               int valueLength,
                                                               @NotNull DataTypeFormatter<T> dataTypeFormatter,
                                                               @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        if (valueLength < 1) {
            throw new IllegalArgumentException("valueLength < 1");
        }
        return new ListDataTypeFormatter<>(prefix, suffix,
                null, stringValue -> stringValue.length() == valueLength, dataTypeFormatter,
                nullSourceSupplier);
    }

    private static void appendNullSafe(@Nullable String text, @NotNull StringBuilder b) {
        if (text != null && !text.isEmpty()) {
            b.append(text);
        }
    }

    private void appendListValues(@NotNull List<T> sourceList, @NotNull StringBuilder b) {
        boolean firstValue = true;
        for (T value : sourceList) {
            // Format value to string
            String stringValue = dataTypeFormatter.format(value);
            // Skip null values
            if (stringValue != null) {
                // Validate formatted string value
                if (stringValueValidator != null && !stringValueValidator.test(stringValue)) {
                    throw new DataTypeFormatException("The list cannot be formatted because a formatted value is not valid.");
                }

                // Add delimiter
                if (firstValue) {
                    firstValue = false;
                } else {
                    appendNullSafe(delimiter, b);
                }

                b.append(stringValue);
            }
        }
    }

    @Override
    public @Nullable String format(@Nullable List<T> source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            StringBuilder b = new StringBuilder();

            // Prefix
            appendNullSafe(prefix, b);

            // List values
            appendListValues(source, b);

            // Suffix
            appendNullSafe(suffix, b);

            return b.toString();
        }
    }

}
