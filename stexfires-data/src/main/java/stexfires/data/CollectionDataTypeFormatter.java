package stexfires.data;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @since 0.1
 */
public final class CollectionDataTypeFormatter<T, C extends Collection<@Nullable T>> implements DataTypeFormatter<C> {

    private final @Nullable String prefix;
    private final @Nullable String suffix;
    private final @Nullable String delimiter;
    private final DataTypeFormatter<T> dataTypeFormatter;
    private final @Nullable Predicate<@Nullable String> formattedElementValidator;
    private final @Nullable Supplier<@Nullable String> nullSourceSupplier;

    public CollectionDataTypeFormatter(@Nullable String prefix,
                                       @Nullable String suffix,
                                       @Nullable String delimiter,
                                       DataTypeFormatter<T> dataTypeFormatter,
                                       @Nullable Predicate<@Nullable String> formattedElementValidator,
                                       @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        this.prefix = prefix;
        this.suffix = suffix;
        this.delimiter = delimiter;
        this.dataTypeFormatter = dataTypeFormatter;
        this.formattedElementValidator = formattedElementValidator;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static <T, C extends Collection<@Nullable T>> CollectionDataTypeFormatter<T, C> withDelimiter(String delimiter,
                                                                                                         @Nullable String prefix,
                                                                                                         @Nullable String suffix,
                                                                                                         DataTypeFormatter<T> dataTypeFormatter,
                                                                                                         @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(dataTypeFormatter);
        if (delimiter.isEmpty()) {
            throw new IllegalArgumentException("delimiter is empty");
        }
        return new CollectionDataTypeFormatter<>(prefix, suffix,
                delimiter, dataTypeFormatter,
                stringValue -> (stringValue == null) || !stringValue.contains(delimiter),
                nullSourceSupplier);
    }

    public static <T, C extends Collection<@Nullable T>> CollectionDataTypeFormatter<T, C> withLength(int length,
                                                                                                      @Nullable String prefix,
                                                                                                      @Nullable String suffix,
                                                                                                      DataTypeFormatter<T> dataTypeFormatter,
                                                                                                      @Nullable Supplier<@Nullable String> nullSourceSupplier) {
        Objects.requireNonNull(dataTypeFormatter);
        if (length < 1) {
            throw new IllegalArgumentException("length < 1");
        }
        return new CollectionDataTypeFormatter<>(prefix, suffix,
                null, dataTypeFormatter,
                stringValue -> (stringValue != null) && (stringValue.length() == length),
                nullSourceSupplier);
    }

    private static void appendNullSafe(@Nullable String text, StringBuilder b) {
        if (text != null && !text.isEmpty()) {
            b.append(text);
        }
    }

    private void appendCollectionElements(C source, StringBuilder b) throws DataTypeConverterException {
        boolean firstElement = true;
        for (@Nullable T element : source) {
            // Format nullable element to nullable string
            String formattedElement = dataTypeFormatter.format(element);

            // Validate formatted element
            if (formattedElementValidator != null && !formattedElementValidator.test(formattedElement)) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Formatter, "A formatted element is not valid.");
            }

            // Add delimiter
            if (firstElement) {
                firstElement = false;
            } else {
                appendNullSafe(delimiter, b);
            }

            appendNullSafe(formattedElement, b);
        }
    }

    @Override
    public @Nullable String format(@Nullable C source) throws DataTypeConverterException {
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
