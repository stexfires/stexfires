package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.time.DateTimeException;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConvertingDataTypeFormatter<T, V> implements DataTypeFormatter<T> {

    private final Function<T, V> dataConverter;
    private final DataTypeFormatter<V> dataTypeFormatter;
    private final UnaryOperator<String> postAdjustment;
    private final Supplier<String> nullSourceSupplier;

    public ConvertingDataTypeFormatter(@NotNull Function<T, V> dataConverter,
                                       @NotNull DataTypeFormatter<V> dataTypeFormatter,
                                       @Nullable UnaryOperator<String> postAdjustment,
                                       @Nullable Supplier<String> nullSourceSupplier) {
        Objects.requireNonNull(dataConverter);
        Objects.requireNonNull(dataTypeFormatter);
        this.dataConverter = dataConverter;
        this.dataTypeFormatter = dataTypeFormatter;
        this.postAdjustment = postAdjustment;
        this.nullSourceSupplier = nullSourceSupplier;
    }

    public static Function<URL, URI> formatterConverterUrlToUri() {
        return url -> {
            try {
                return url.toURI();
            } catch (URISyntaxException e) {
                throw new DataTypeFormatException("Cannot convert URL to URI");
            }
        };
    }

    @Override
    public @Nullable String format(@Nullable T source) throws DataTypeFormatException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else {
            try {
                String formattedString = dataTypeFormatter.format(dataConverter.apply(source));
                return (postAdjustment == null) ? formattedString : postAdjustment.apply(formattedString);
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeFormatException("Cannot format source: " + e.getClass().getName());
            }
        }
    }

}
