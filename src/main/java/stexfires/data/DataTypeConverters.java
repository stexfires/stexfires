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
 * This class consists of {@code static} utility methods
 * for converting data types.
 * <p>
 * All methods return a {@link java.util.function.Function}.
 * All methods are not null-safe and throw a {@link java.lang.NullPointerException}.
 * All other possible exceptions are caught and thrown as a new {@link stexfires.data.DataTypeConverterException}.
 *
 * @author Mathias Kalb
 * @see java.util.function.Function
 * @see stexfires.data.ConvertingDataTypeFormatter
 * @see stexfires.data.ConvertingDataTypeParser
 * @since 0.1
 */
public final class DataTypeConverters {

    private DataTypeConverters() {
    }

    public static Function<URL, URI> URL_to_URI() {
        return url -> {
            try {
                return url.toURI();
            } catch (URISyntaxException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert URL to URI.", e);
            }
        };
    }

    public static Function<Path, URI> Path_to_URI() {
        return Path::toUri;
    }

    public static Function<File, URI> File_to_URI() {
        return File::toURI;
    }

    public static Function<URI, Path> URI_to_Path() {
        return uri -> {
            try {
                return Path.of(uri);
            } catch (IllegalArgumentException | FileSystemNotFoundException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert URI to Path.", e);
            }
        };
    }

    public static Function<File, Path> File_to_Path() {
        return file -> {
            try {
                return file.toPath();
            } catch (InvalidPathException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert File to Path.", e);
            }
        };
    }

    public static Function<Instant, Long> Instant_to_Long_EpochSecond() {
        return Instant::getEpochSecond;
    }

    public static Function<Instant, Long> Instant_to_Long_EpochMilli() {
        return instant -> {
            try {
                return instant.toEpochMilli();
            } catch (ArithmeticException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert Instant to Long.", e);
            }
        };
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    public static Function<Date, Instant> Date_to_Instant() {
        return Date::toInstant;
    }

    public static Function<URI, URL> URI_to_URL() {
        return uri -> {
            try {
                return uri.toURL();
            } catch (IllegalArgumentException | MalformedURLException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert URI to URL.", e);
            }
        };
    }

    public static Function<URI, File> URI_to_File() {
        return uri -> {
            try {
                return new File(uri);
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert URI to File.", e);
            }
        };
    }

    public static Function<Path, File> Path_to_File() {
        return path -> {
            try {
                return path.toFile();
            } catch (UnsupportedOperationException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert Path to File.", e);
            }
        };
    }

    public static Function<Long, Instant> Long_to_Instant_EpochSecond() {
        return epochSecond -> {
            try {
                return Instant.ofEpochSecond(epochSecond);
            } catch (DateTimeException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert Long to Instant.", e);
            }
        };
    }

    public static Function<Long, Instant> Long_to_Instant_EpochMilli() {
        return epochMilli -> {
            try {
                return Instant.ofEpochMilli(epochMilli);
            } catch (DateTimeException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert Long to Instant.", e);
            }
        };
    }

    @SuppressWarnings("UseOfObsoleteDateTimeApi")
    public static Function<Instant, Date> Instant_to_Date() {
        return instant -> {
            try {
                return Date.from(instant);
            } catch (IllegalArgumentException e) {
                throw new DataTypeConverterException(Converter, "Cannot convert Instant to Date.", e);
            }
        };
    }

}
