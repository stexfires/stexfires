package stexfires.data;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import static stexfires.data.DataTypeConverterException.Type.Converter;

/**
 * This class consists of constant utility functions
 * for converting data types.
 * <p>
 * All {@link java.util.function.Function}s are not null-safe and throw a {@link java.lang.NullPointerException}.
 * All other possible exceptions are caught and thrown as a new {@link stexfires.data.DataTypeConverterException}.
 *
 * @see java.util.function.Function
 * @see stexfires.data.ConvertingDataTypeFormatter
 * @see stexfires.data.ConvertingDataTypeParser
 * @since 0.1
 */
public final class DataTypeConverters {

    public static final Function<Path, File> PATH_TO_FILE = (path) -> {
        try {
            return path.toFile();
        } catch (UnsupportedOperationException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert Path to File.", e);
        }
    };

    public static final Function<File, Path> FILE_TO_PATH = (file) -> {
        try {
            return file.toPath();
        } catch (InvalidPathException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert File to Path.", e);
        }
    };

    public static final Function<Path, URI> PATH_TO_URI = Path::toUri;

    public static final Function<URI, Path> URI_TO_PATH = (uri) -> {
        try {
            return Path.of(uri);
        } catch (IllegalArgumentException | FileSystemNotFoundException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert URI to Path.", e);
        }
    };

    public static final Function<File, URI> FILE_TO_URI = File::toURI;

    public static final Function<URI, File> URI_TO_FILE = (uri) -> {
        try {
            return new File(uri);
        } catch (IllegalArgumentException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert URI to File.", e);
        }
    };

    public static final Function<URL, URI> URL_TO_URI = (url) -> {
        try {
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert URL to URI.", e);
        }
    };

    public static final Function<URI, URL> URI_TO_URL = (uri) -> {
        try {
            return uri.toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert URI to URL.", e);
        }
    };

    public static final Function<Instant, Long> INSTANT_TO_LONG_EPOCH_SECOND =
            Instant::getEpochSecond;

    public static final Function<Long, Instant> LONG_TO_INSTANT_EPOCH_SECOND = (epochSecond) -> {
        try {
            return Instant.ofEpochSecond(epochSecond);
        } catch (DateTimeException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert Long to Instant.", e);
        }
    };

    public static final Function<Instant, Long> INSTANT_TO_LONG_EPOCH_MILLI = (instant) -> {
        try {
            return instant.toEpochMilli();
        } catch (ArithmeticException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert Instant to Long.", e);
        }
    };

    public static final Function<Long, Instant> LONG_TO_INSTANT_EPOCH_MILLI = (epochMilli) -> {
        try {
            return Instant.ofEpochMilli(epochMilli);
        } catch (DateTimeException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert Long to Instant.", e);
        }
    };

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    public static final Function<Date, Instant> DATE_TO_INSTANT = Date::toInstant;

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    public static final Function<Instant, Date> INSTANT_TO_DATE = (instant) -> {
        try {
            return Date.from(instant);
        } catch (IllegalArgumentException e) {
            throw new DataTypeConverterException(Converter, "Cannot convert Instant to Date.", e);
        }
    };

    private DataTypeConverters() {
    }

}
