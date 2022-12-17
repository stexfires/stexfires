package stexfires.io.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import stexfires.io.ReadableRecordFileSpec;
import stexfires.io.WritableRecordFileSpec;
import stexfires.record.KeyValueCommentRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;
import stexfires.util.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 * @author Mathias Kalb
 * @since 0.1
 */
public record ConfigFileSpec(
        @NotNull CharsetCoding charsetCoding,
        @NotNull String valueDelimiter,
        @NotNull String commentLinePrefix,
        @NotNull LineSeparator consumerLineSeparator,
        boolean consumerSeparateCategoriesByLine,
        boolean consumerSeparateByWhitespace,
        @Nullable String consumerCommentLinesBefore
) implements ReadableRecordFileSpec<KeyValueCommentRecord, ConfigProducer>, WritableRecordFileSpec<KeyValueCommentRecord, ConfigConsumer> {

    public static final String DEFAULT_VALUE_DELIMITER = "=";
    public static final String DEFAULT_COMMENT_LINE_PREFIX = ";";
    public static final boolean DEFAULT_CONSUMER_SEPARATE_CATEGORIES_BY_LINE = false;
    public static final boolean DEFAULT_CONSUMER_SEPARATE_BY_WHITESPACE = false;
    public static final String DEFAULT_CONSUMER_COMMENT_LINES_BEFORE = null;

    public static final String ALTERNATIVE_VALUE_DELIMITER_COLON = ":";
    public static final String ALTERNATIVE_VALUE_DELIMITER_WHITE_SPACE = " ";
    public static final String ALTERNATIVE_COMMENT_LINE_PREFIX_NUMBER_SIGN = "#";

    public static final String NULL_CATEGORY = "";
    public static final String NULL_KEY = "";
    public static final String NULL_VALUE = "";
    public static final String CATEGORY_BEGIN_MARKER = "[";
    public static final String CATEGORY_END_MARKER = "]";
    public static final String WHITESPACE_SEPARATOR = Strings.SPACE;

    public ConfigFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(valueDelimiter);
        Objects.requireNonNull(commentLinePrefix);
        Objects.requireNonNull(consumerLineSeparator);
    }

    public static ConfigFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding) {
        return new ConfigFileSpec(
                charsetCoding,
                DEFAULT_VALUE_DELIMITER,
                DEFAULT_COMMENT_LINE_PREFIX,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_SEPARATE_CATEGORIES_BY_LINE,
                DEFAULT_CONSUMER_SEPARATE_BY_WHITESPACE,
                DEFAULT_CONSUMER_COMMENT_LINES_BEFORE
        );
    }

    public static ConfigFileSpec producerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                  @NotNull String valueDelimiter,
                                                  @NotNull String commentLinePrefix) {
        return new ConfigFileSpec(
                charsetCoding,
                valueDelimiter,
                commentLinePrefix,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_SEPARATE_CATEGORIES_BY_LINE,
                DEFAULT_CONSUMER_SEPARATE_BY_WHITESPACE,
                DEFAULT_CONSUMER_COMMENT_LINES_BEFORE
        );
    }

    public static ConfigFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                  @NotNull LineSeparator consumerLineSeparator) {
        return new ConfigFileSpec(
                charsetCoding,
                DEFAULT_VALUE_DELIMITER,
                DEFAULT_COMMENT_LINE_PREFIX,
                consumerLineSeparator,
                DEFAULT_CONSUMER_SEPARATE_CATEGORIES_BY_LINE,
                DEFAULT_CONSUMER_SEPARATE_BY_WHITESPACE,
                DEFAULT_CONSUMER_COMMENT_LINES_BEFORE
        );
    }

    public static ConfigFileSpec consumerFileSpec(@NotNull CharsetCoding charsetCoding,
                                                  @NotNull String valueDelimiter,
                                                  @NotNull String commentLinePrefix,
                                                  @NotNull LineSeparator consumerLineSeparator,
                                                  boolean consumerSeparateCategoriesByLine,
                                                  boolean consumerSeparateByWhitespace,
                                                  @Nullable String consumerCommentLinesBefore) {
        return new ConfigFileSpec(
                charsetCoding,
                valueDelimiter,
                commentLinePrefix,
                consumerLineSeparator,
                consumerSeparateCategoriesByLine,
                consumerSeparateByWhitespace,
                consumerCommentLinesBefore
        );
    }

    @Override
    public ConfigProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new ConfigProducer(bufferedReader, this);
    }

    @Override
    public ConfigConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new ConfigConsumer(bufferedWriter, this);
    }

}
