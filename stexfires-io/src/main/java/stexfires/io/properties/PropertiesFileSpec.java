package stexfires.io.properties;

import org.jspecify.annotations.Nullable;
import stexfires.io.consumer.WritableRecordFileSpec;
import stexfires.io.producer.ReadableRecordFileSpec;
import stexfires.record.KeyValueRecord;
import stexfires.util.CharsetCoding;
import stexfires.util.LineSeparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.*;

/**
 * @since 0.1
 */
public record PropertiesFileSpec(
        CharsetCoding charsetCoding,
        @Nullable String producerNullValueReplacement,
        boolean producerCommentAsCategory,
        LineSeparator consumerLineSeparator,
        String consumerNullValueReplacement,
        boolean consumerEscapeUnicode,
        boolean consumerDateComment,
        boolean consumerCategoryAsKeyPrefix,
        String consumerKeyPrefixDelimiter
) implements ReadableRecordFileSpec<KeyValueRecord, PropertiesProducer>,
             WritableRecordFileSpec<KeyValueRecord, PropertiesConsumer> {

    public static final String DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_PRODUCER_COMMENT_AS_CATEGORY = false;
    public static final String DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT = "";
    public static final boolean DEFAULT_CONSUMER_ESCAPE_UNICODE = false;
    public static final boolean DEFAULT_CONSUMER_DATE_COMMENT = false;
    public static final boolean DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX = false;
    public static final String DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER = ".";

    public static final String DELIMITER = "=";
    public static final String COMMENT_PREFIX = "#";
    public static final int FIRST_NON_ESCAPED_CHAR = 0x0020;
    public static final int LAST_NON_ESCAPED_CHAR = 0x007e;
    public static final char ESCAPE_CHAR = '\\';
    public static final int UNICODE_ENCODE_LENGTH = 4;
    public static final int UNICODE_ENCODE_RADIX = 16;

    public PropertiesFileSpec {
        Objects.requireNonNull(charsetCoding);
        Objects.requireNonNull(consumerLineSeparator);
        Objects.requireNonNull(consumerNullValueReplacement);
        Objects.requireNonNull(consumerKeyPrefixDelimiter);
    }

    public static PropertiesFileSpec producerFileSpec(CharsetCoding charsetCoding) {
        return new PropertiesFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                DEFAULT_CONSUMER_ESCAPE_UNICODE,
                DEFAULT_CONSUMER_DATE_COMMENT,
                DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec producerFileSpec(CharsetCoding charsetCoding,
                                                      @Nullable String producerNullValueReplacement,
                                                      boolean producerCommentAsCategory) {
        return new PropertiesFileSpec(
                charsetCoding,
                producerNullValueReplacement,
                producerCommentAsCategory,
                DEFAULT_CONSUMER_LINE_SEPARATOR,
                DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                DEFAULT_CONSUMER_ESCAPE_UNICODE,
                DEFAULT_CONSUMER_DATE_COMMENT,
                DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                      LineSeparator consumerLineSeparator) {
        return new PropertiesFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                consumerLineSeparator,
                DEFAULT_CONSUMER_NULL_VALUE_REPLACEMENT,
                DEFAULT_CONSUMER_ESCAPE_UNICODE,
                DEFAULT_CONSUMER_DATE_COMMENT,
                DEFAULT_CONSUMER_CATEGORY_AS_KEY_PREFIX,
                DEFAULT_CONSUMER_KEY_PREFIX_DELIMITER);
    }

    public static PropertiesFileSpec consumerFileSpec(CharsetCoding charsetCoding,
                                                      LineSeparator consumerLineSeparator,
                                                      String consumerNullValueReplacement,
                                                      boolean consumerEscapeUnicode,
                                                      boolean consumerDateComment,
                                                      boolean consumerCategoryAsKeyPrefix,
                                                      String consumerKeyPrefixDelimiter) {
        return new PropertiesFileSpec(
                charsetCoding,
                DEFAULT_PRODUCER_NULL_VALUE_REPLACEMENT,
                DEFAULT_PRODUCER_COMMENT_AS_CATEGORY,
                consumerLineSeparator,
                consumerNullValueReplacement,
                consumerEscapeUnicode,
                consumerDateComment,
                consumerCategoryAsKeyPrefix,
                consumerKeyPrefixDelimiter);
    }

    @Override
    public PropertiesProducer producer(BufferedReader bufferedReader) {
        Objects.requireNonNull(bufferedReader);
        return new PropertiesProducer(bufferedReader, this);
    }

    @Override
    public PropertiesConsumer consumer(BufferedWriter bufferedWriter) {
        Objects.requireNonNull(bufferedWriter);
        return new PropertiesConsumer(bufferedWriter, this);
    }

}
