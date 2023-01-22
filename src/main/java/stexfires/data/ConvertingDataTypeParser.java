package stexfires.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public final class ConvertingDataTypeParser<T, V> implements DataTypeParser<T> {

    private final UnaryOperator<String> preparatoryAdjustment;
    private final DataTypeParser<V> dataTypeParser;
    private final Function<V, T> dataConverter;
    private final Supplier<T> nullSourceSupplier;
    private final Supplier<T> emptySourceSupplier;

    public ConvertingDataTypeParser(@Nullable UnaryOperator<String> preparatoryAdjustment,
                                    @NotNull DataTypeParser<V> dataTypeParser,
                                    @NotNull Function<V, T> dataConverter,
                                    @Nullable Supplier<T> nullSourceSupplier,
                                    @Nullable Supplier<T> emptySourceSupplier) {
        Objects.requireNonNull(dataTypeParser);
        Objects.requireNonNull(dataConverter);
        this.preparatoryAdjustment = preparatoryAdjustment;
        this.dataTypeParser = dataTypeParser;
        this.dataConverter = dataConverter;
        this.nullSourceSupplier = nullSourceSupplier;
        this.emptySourceSupplier = emptySourceSupplier;
    }

    public static Function<URI, URL> parserConverterUriToUrl() {
        return uri -> {
            try {
                return uri.toURL();
            } catch (IllegalArgumentException | MalformedURLException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert URI to URL");
            }
        };
    }

    public static Function<URI, Path> parserConverterUriToPath() {
        return uri -> {
            try {
                return Path.of(uri);
            } catch (IllegalArgumentException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert URI to Path");
            }
        };
    }

    public static Function<URI, File> parserConverterUriToFile() {
        return uri -> {
            try {
                return new File(uri);
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert URI to File");
            }
        };
    }

    public static Function<Path, URI> parserConverterPathToUri() {
        return Path::toUri;
    }

    public static Function<Path, File> parserConverterPathToFile() {
        return path -> {
            try {
                return path.toFile();
            } catch (UnsupportedOperationException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert Path to File");
            }
        };
    }

    public static Function<Long, Instant> parserConverterLongEpochSecondToInstant() {
        return epochSecond -> {
            try {
                return Instant.ofEpochSecond(epochSecond);
            } catch (DateTimeException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert Long to Instant");
            }
        };
    }

    public static Function<Long, Instant> parserConverterLongEpochMilliToInstant() {
        return epochMilli -> {
            try {
                return Instant.ofEpochMilli(epochMilli);
            } catch (DateTimeException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert Long to Instant");
            }
        };
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    public static Function<Instant, Date> parserConverterInstantToDate() {
        return instant -> {
            try {
                return Date.from(instant);
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot convert Instant to Date");
            }
        };
    }

    @Override
    public @Nullable T parse(@Nullable String source) throws DataTypeConverterException {
        if (source == null) {
            return handleNullSource(nullSourceSupplier);
        } else if (source.isEmpty()) {
            return handleEmptySource(emptySourceSupplier);
        } else {
            try {
                String preparedString = (preparatoryAdjustment == null) ? source : preparatoryAdjustment.apply(source);

                return dataConverter.apply(dataTypeParser.parse(preparedString));
            } catch (IllegalArgumentException | NullPointerException | UncheckedIOException | ClassCastException |
                     IllegalStateException | IndexOutOfBoundsException | ArithmeticException | DateTimeException |
                     UnsupportedOperationException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(DataTypeConverterException.Type.Parser, "Cannot parse source: " + e.getClass().getName());
            }
        }
    }

}
