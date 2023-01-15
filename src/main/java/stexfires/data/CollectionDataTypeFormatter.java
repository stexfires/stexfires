package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class CollectionDataTypeFormatter<T, C extends Collection<T>> implements DataTypeFormatter<C> {

    private final String prefix;
    private final String suffix;
    private final String delimiter;
    private final Predicate<String> formattedElementValidator;
    private final DataTypeFormatter<T> dataTypeFormatter;
    private final Supplier<String> nullSourceSupplier;

    public CollectionDataTypeFormatter(
            @Nullable String prefix,
            @Nullable String suffix,
            @Nullable String delimiter,
            @NotNull DataTypeFormatter<T> dataTypeFormatter,
            @Nullable Predicate<String> formattedElementValidator,
            @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        this.prefix = prefix;
        this.suffix = suffix;
        this.delimiter = delimiter;
        this.dataTypeFormatter = dataTypeFormatter;
        this.formattedElementValidator = formattedElementValidator;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static <T, C extends Collection<T>> CollectionDataTypeFormatter<T, C> withDelimiter(@NotNull String delimiter,
                                                                                               @Nullable String prefix,
                                                                                               @Nullable String suffix,
                                                                                               @NotNull DataTypeFormatter<T> dataTypeFormatter,
                                                                                               @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(dataTypeFormatter);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter is empty");
        }
        return new CollectionDataTypeFormatter<>(prefix, suffix,
                delimiter, dataTypeFormatter,
                stringValue -> !stringValue.contains(delimiter),
                nullSourceSupplier);
    }

    public static <T, C extends Collection<T>> CollectionDataTypeFormatter<T, C> withLength(int length,
                                                                                            @Nullable String prefix,
                                                                                            @Nullable String suffix,
                                                                                            @NotNull DataTypeFormatter<T> dataTypeFormatter,
                                                                                            @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
        return new CollectionDataTypeFormatter<>(prefix, suffix,
                null, dataTypeFormatter,
                stringValue -> stringValue.length() == length,
                nullSourceSupplier);
    }

    private static void appendNullSafe(@Nullable String text, @NotNull StringBuilder b) {
        if (text != null && !text.isEmpty()) {
            b.append(text);
        }
    }

    private void appendCollectionElements(@NotNull C source, @NotNull StringBuilder b) throws DataTypeFormatException {
        boolean firstElement = true;
        for (T element : source) {
            // Format element to string
            String formattedElement = dataTypeFormatter.format(element);
            // Skip null
            if (formattedElement != null) {
                // Validate formatted element
                if (formattedElementValidator != null && !formattedElementValidator.test(formattedElement)) {
                    throw new DataTypeFormatException("A formatted element is not valid.");
                }

                // Add delimiter
                if (firstElement) {
                    firstElement = false;
                } else {
                    appendNullSafe(delimiter, b);
                }

                b.append(formattedElement);
            }
        }
    }

    @Override
    public @Nullable String format(@Nullable C source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            StringBuilder b = new StringBuilder();

            // Prefix
            appendNullSafe(prefix, b);

            // Collection elements
            appendCollectionElements(source, b);

            // Suffix
            appendNullSafe(suffix, b);

            return b.toString();
        }
    }

}
